package edu.stanford.nlp.sempre.nlvr;

import com.fasterxml.jackson.databind.JsonNode;
import edu.stanford.nlp.sempre.*;
import fig.basic.LispTree;
import fig.basic.Pair;

import java.util.*;

/**
 * Knowledge graph constructed from an image in the NLVR dataset.
 *
 * Each box is an entity (fb:box)
 * Each object within a box is an entity (fb:object)
 * Each object has relations with the following properties
 *      fb:object.object.x_loc: <fb:object, number>
 *      fb:object.object.y_loc: <fb:object, number>
 *      fb:object.object.shape: <fb:object, shape>
 *      fb:object.object.color: <fb:object, color>
 * Each box has relations to the objects within it
 *      fb:box.box.object: <fb:box, fb:object>
 */

public class NLVRKnowledgeGraph extends KnowledgeGraph implements FuzzyMatchable {
  public List<NLVRBox> boxes;
  public List<NLVRObject> objects;
  public String identifier;
  public Set<Formula> allEntityFormulas;
  public Set<Formula> allUnaryFormulas;

  /**
   * Create a knowledge graph from a json node. We assume this node was passed
   * by some code that reads the json data file. Also takes an identifier to help link
   * this to the utterance.
   * @param structuredRep
   * @param identifier
   */
  public NLVRKnowledgeGraph(JsonNode structuredRep, String identifier) {
    this.identifier = identifier;
    // The boxes and objects will be identified by indices.
    int boxIndex = 0;
    int objectIndex = 0;
    Iterator<JsonNode> boxNodes = structuredRep.elements();
    // Iterating over boxes
    while (boxNodes.hasNext()) {
      NLVRBox box = new NLVRBox(boxIndex);
      Iterator<JsonNode> objectNodes = boxNodes.next().elements();
      // Iterating over objects within each box
      while (objectNodes.hasNext()) {
        JsonNode objectNode = objectNodes.next();
        NLVRObject object = new NLVRObject(objectIndex, objectNode.get("size").asInt(),
                objectNode.get("type").asText(), objectNode.get("color").asText(),
                objectNode.get("x_loc").asInt(), objectNode.get("y_loc").asInt());
        objects.add(object);
        box.children.add(object);
        objectIndex ++;
      }
      boxes.add(box);
      boxIndex ++;
    }
    allEntityFormulas = new HashSet<>();
    allUnaryFormulas = new HashSet<>();
    precompute();
  }

  private void precompute() {
    for (NLVRObject object: objects) {
      allUnaryFormulas.add(getUnaryFormulaByColor(object.color));
      allUnaryFormulas.add(getUnaryFormulaByShape(object.shape));
    }
  }

  static Formula getUnaryFormulaByColor(NLVRObject.Color color) {
    // Get objects given color: (!nlvr:object.color nlvr:color.___)
    return new JoinFormula(
        new ValueFormula<>(CanonicalNames.reverseProperty(NLVRTypeSystem.OBJECT_COLOR_RELATION)),
        new ValueFormula<>(NLVRObject.COLOR_NAME_VALUES.get(color))
    );
  }

  static Formula getUnaryFormulaByShape(NLVRObject.Shape shape) {
    // Get objects given shape: (!nlvr:object.shape nlvr:shape.___)
    return new JoinFormula(
        new ValueFormula<>(CanonicalNames.reverseProperty(NLVRTypeSystem.OBJECT_SHAPE_RELATION)),
        new ValueFormula<>(NLVRObject.SHAPE_NAME_VALUES.get(shape))
    );
  }

  @Override
  public Collection<Formula> getFuzzyMatchedFormulas(List<String> sentence, int startIndex, int endIndex, FuzzyMatchFn.FuzzyMatchFnMode mode) {
    String term = String.join(" ", sentence.subList(startIndex, endIndex));
    return getFuzzyMatchedFormulas(term, mode);
  }

  @Override
  public Collection<Formula> getFuzzyMatchedFormulas(String term, FuzzyMatchFn.FuzzyMatchFnMode mode) {
    return null;
  }

  @Override
  public Collection<Formula> getAllFormulas(FuzzyMatchFn.FuzzyMatchFnMode mode) {
    return null;
  }

  @Override
  public LispTree toLispTree() {
    return null;
  }

  @Override
  public LispTree toShortLispTree() {
    return null;
  }

  @Override
  public List<Value> joinFirst(Value r, Collection<Value> firsts) {
    return null;
  }

  @Override
  public List<Value> joinSecond(Value r, Collection<Value> seconds) {
    return null;
  }

  @Override
  public List<Pair<Value, Value>> filterFirst(Value r, Collection<Value> firsts) {
    return null;
  }

  @Override
  public List<Pair<Value, Value>> filterSecond(Value r, Collection<Value> seconds) {
    return null;
  }
}