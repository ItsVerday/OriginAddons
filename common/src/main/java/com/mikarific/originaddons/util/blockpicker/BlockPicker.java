package com.mikarific.originaddons.util.blockpicker;

import com.mikarific.originaddons.OriginAddons;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.input.BOMInputStream;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlockPicker {
    public static final String REMAP_FILE = "assets/originaddons/other/blockpick.json";

    private static final Map<Block, Map<BlockState, String>> blockTypes = new HashMap<>();
    private static Map<String, String> remap = null;
    private static List<String> numbered = null;
    public static boolean isEnabled() {
        return OriginAddons.onOriginRealms() && OriginAddons.getConfig().blockPicker;
    }

    public static void clear() {
        blockTypes.clear();
        clearRemap();
    }

    public static void clearRemap() {
        remap = null;
        numbered = null;
    }

    public static String getCustomBlockName(BlockState blockState) {
        Block block = blockState.getBlock();
        if (!blockTypes.containsKey(block)) return "";

        Map<BlockState, String> states = blockTypes.get(block);
        if (!states.containsKey(blockState)) return "";

        String modelName = states.get(blockState);
        if (getRemap().containsKey(modelName)) {
            return getRemap().get(modelName);
        }

        return modelName;
    }

    public static void processBlockStateFile(JsonObject json, Identifier blockIdentifier) {
        Block block = Registry.BLOCK.get(blockIdentifier);

        if (json.has("variants")) {
            JsonObject variants = json.get("variants").getAsJsonObject();
            for (String key: variants.keySet()) processBlockStateVariant(block, variants, key);
        }

        if (json.has("multipart")) {
            JsonArray multipart = json.get("multipart").getAsJsonArray();
            for (JsonElement part: multipart) processMultipart(block, part.getAsJsonObject());
        }
    }

    private static void processBlockStateVariant(Block block, JsonObject variants, String key) {
        Identifier modelIdentifier = getModelIdentifier(variants.get(key));
        if (!isCustomModel(block, modelIdentifier)) return;

        StateManager<Block, BlockState> states = block.getStateManager();
        Map<Property<?>, String> properties = stateKeyToProperties(states, key);

        processBlockStates(block, states, properties, cleanModelName(modelIdentifier.getPath()));
    }

    private static void processMultipart(Block block, JsonObject multipart) {
        Identifier modelIdentifier = getModelIdentifier(multipart.get("apply"));
        if (!isCustomModel(block, modelIdentifier)) return;

        JsonObject when = multipart.get("when").getAsJsonObject();
        StateManager<Block, BlockState> states = block.getStateManager();
        Map<Property<?>, String> properties = multipartWhenToProperties(states, when);

        processBlockStates(block, states, properties, cleanModelName(modelIdentifier.getPath()));
    }

    private static void processBlockStates(Block block, StateManager<Block, BlockState> states, Map<Property<?>, String> properties, String modelName) {
        for (BlockState blockState: states.getStates()) {
            boolean matches = true;

            for (Property<?> property: properties.keySet()) {
                if (!blockState.get(property).toString().equalsIgnoreCase(properties.get(property))) {
                    matches = false;
                    break;
                }
            }

            if (!matches) continue;
            addCustomBlock(block, blockState, modelName);
        }
    }

    private static void addCustomBlock(Block block, BlockState blockState, String modelName) {
        if (!blockTypes.containsKey(block)) blockTypes.put(block, new HashMap<>());

        Map<BlockState, String> states = blockTypes.get(block);
        states.put(blockState, modelName);
    }

    private static boolean loadBlockpickJSON() {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(REMAP_FILE);
        try (InputStream in = new BOMInputStream(inputStream); InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            JsonObject blockpickJSON = JsonParser.parseReader(reader).getAsJsonObject();
            JsonObject remapObject = blockpickJSON.get("remap").getAsJsonObject();

            remap = new HashMap<>();
            for (String from: remapObject.keySet()) {
                remap.put(from, remapObject.get(from).getAsString());
            }

            numbered = new ArrayList<>();
            for (JsonElement numberedName: blockpickJSON.get("numbered").getAsJsonArray()) {
                numbered.add(numberedName.getAsString());
            }

            return true;
        } catch (Exception e) {
            OriginAddons.LOGGER.error("Error occurred processing blockpick.json!", e);
            return false;
        }
    }

    private static Map<String, String> getRemap() {
        if (remap == null && !loadBlockpickJSON()) return new HashMap<>();
        return remap;
    }

    private static List<String> getNumbered() {
        if (numbered == null && !loadBlockpickJSON()) return new ArrayList<>();
        return numbered;
    }

    private static boolean isCustomModel(Block block, Identifier modelIdentifier) {
        boolean isCustom = modelIdentifier.getPath().contains("custom/");
        boolean isNoteBlock = Registry.BLOCK.getId(block).equals(Registry.BLOCK.getId(Blocks.NOTE_BLOCK));
        boolean isVanillaOre = modelIdentifier.getPath().startsWith("block/") && modelIdentifier.getPath().endsWith("_ore");

        if (isNoteBlock) return isCustom || isVanillaOre;
        return isCustom;
    }

    private static Identifier getModelIdentifier(JsonElement modelDefinition) {
        if (modelDefinition.isJsonPrimitive()) {
            return Identifier.tryParse(modelDefinition.getAsString());
        } else if (modelDefinition.isJsonArray()) {
            return getModelIdentifier(modelDefinition.getAsJsonArray().get(0));
        } else {
            return getModelIdentifier(modelDefinition.getAsJsonObject().get("model"));
        }
    }

    private static final Pattern NUMBERED_PATTERN = Pattern.compile("^(.*)_\\d+$");

    public static String cleanModelName(String modelName) {
        String[] split = modelName.split("/");
        String id = split[split.length - 1];
        id = id.replaceAll("[ \\-]", "_");
        Matcher matcher = NUMBERED_PATTERN.matcher(id);
        if (matcher.find()) {
            String name = matcher.group(1);
            if (!getNumbered().contains(name)) id = name;
        }

        return id;
    }

    private static Map<Property<?>, String> stateKeyToProperties(StateManager<Block, BlockState> stateManager, String key) {
        List<Pair<String, String>> properties = new ArrayList<>();
        for (String keyValue: key.split(",")) {
            String[] keyValueSplit = keyValue.split("=");
            String propertyKey = keyValueSplit[0];
            String propertyValue = keyValueSplit[1];

            properties.add(new Pair<>(propertyKey, propertyValue));
        }

        return verifyProperties(stateManager, properties);
    }

    private static Map<Property<?>, String> multipartWhenToProperties(StateManager<Block, BlockState> stateManager, JsonObject when) {
        List<Pair<String, String>> properties = new ArrayList<>();
        for (String key: when.keySet()) {
            properties.add(new Pair<>(key, when.get(key).getAsString()));
        }

        return verifyProperties(stateManager, properties);
    }

    private static Map<Property<?>, String> verifyProperties(StateManager<Block, BlockState> stateManager, List<Pair<String, String>> properties) {
        Collection<Property<?>> blockStateProperties = stateManager.getProperties();
        Map<Property<?>, String> finalProperties = new HashMap<>();

        for (Pair<String, String> propertyKeyValue: properties) {
            String propertyKey = propertyKeyValue.getLeft();
            String propertyValue = propertyKeyValue.getRight();

            Property<?> property = null;
            for (Property<?> testProperty: blockStateProperties) {
                if (testProperty.getName().equals(propertyKey)) {
                    property = testProperty;
                    break;
                }
            }

            if (property == null) throw new RuntimeException("Unknown blockstate property: '" + propertyKey + "'");

            Optional<String> value = property.parse(propertyValue).map(Object::toString);
            if (value.isEmpty()) throw new RuntimeException("Unknown value: '" + propertyValue + "' for blockstate property: '" + propertyKey + "' " + property.getValues());

            finalProperties.put(property, propertyValue);
        }

        return finalProperties;
    }
}
