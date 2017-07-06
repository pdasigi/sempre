package edu.stanford.nlp.sempre.nlvr;

import edu.stanford.nlp.sempre.CanonicalNames;
import edu.stanford.nlp.sempre.NameValue;
import edu.stanford.nlp.sempre.SemType;
import edu.stanford.nlp.sempre.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * Typing system for figures in NLVR dataset.
 *
 * Basic types:
 * BOX: nlvr:type.box
 * OBJECT: nlvr:type.object
 * COLOR: nlvr:type.color
 * SHAPE: nlvr:type.shape
 *
 * Entities:
 * BOX: name = nlvr:box.b[index]          | type = nlvr:type.box
 * OBJECT: name = nlvr:object.obj[index]  | type = nlvr:type.object
 * COLOR: name = nlvr:color.[name]        | type = nlvr:type.color
 * SHAPE: name = nlvr:shape.[name]        | type = nlvr:type.shape
 *
 * Relations:
 * COLOR: name = nlvr:color.object        | type = (-> nlvr:type.color nlvr:type.object)
 * SHAPE: name = nlvr:shape.object        | type = (-> nlvr:type.shape nlvr:type.object)
 * XPOS: name = nlvr:xpos.object          | type = (-> fb:type.int nlvr:type.object)
 * YPOS: name = nlvr:ypos.object          | type = (-> fb:type.int nlvr:type.object)
 * SIZE: name = nlvr:size.object          | type = (-> fb:type.int nlvr:type.object)
 * CONTAINS: name = nlvr:object.box       | type = (-> nlvr:type.object nlvr:type.box)
 *
 * @author pdasigi
 */
public abstract class NLVRTypeSystem {
  static final String BOX_NAME_PREFIX = "nlvr:box.b";
  static final String OBJECT_NAME_PREFIX = "nlvr:object.obj";
  static final String COLOR_NAME_PREFIX = "nlvr:color";
  static final String SHAPE_NAME_PREFIX = "nlvr:shape";

  // Defining all types
  public static final String BOX_TYPE_NAME = "nlvr:type.box";
  public static final String OBJECT_TYPE_NAME = "nlvr:type.object";
  public static final String COLOR_TYPE_NAME = "nlvr:type.color";
  public static final String SHAPE_TYPE_NAME = "nlvr:type.shape";
  public static final SemType BOX_TYPE = SemType.newAtomicSemType(BOX_TYPE_NAME);
  public static final SemType OBJECT_TYPE = SemType.newAtomicSemType(OBJECT_TYPE_NAME);
  public static final SemType COLOR_TYPE = SemType.newAtomicSemType(COLOR_TYPE_NAME);
  public static final SemType SHAPE_TYPE = SemType.newAtomicSemType(SHAPE_TYPE_NAME);

  // Defining all relations
  public static final NameValue OBJECT_COLOR_RELATION = new NameValue("nlvr:color.object");
  public static final NameValue OBJECT_SHAPE_RELATION = new NameValue("nlvr:shape.object");
  public static final NameValue OBJECT_XPOS_RELATION = new NameValue("nlvr:xpos.object");
  public static final NameValue OBJECT_YPOS_RELATION = new NameValue("nlvr:ypos.object");
  public static final NameValue OBJECT_SIZE_RELATION = new NameValue("nlvr:size.object");
  public static final NameValue OBJECT_IN_BOX_RELATION = new NameValue("nlvr:object.box");
  public static final Map<Value, SemType> OBJECT_RELATIONS = new HashMap<>();
  static {
    OBJECT_RELATIONS.put(OBJECT_COLOR_RELATION, SemType.newFuncSemType(OBJECT_TYPE_NAME, COLOR_TYPE_NAME));
    OBJECT_RELATIONS.put(OBJECT_SHAPE_RELATION, SemType.newFuncSemType(OBJECT_TYPE_NAME, SHAPE_TYPE_NAME));
    OBJECT_RELATIONS.put(OBJECT_XPOS_RELATION, SemType.newFuncSemType(OBJECT_TYPE_NAME, CanonicalNames.INT));
    OBJECT_RELATIONS.put(OBJECT_YPOS_RELATION, SemType.newFuncSemType(OBJECT_TYPE_NAME, CanonicalNames.INT));
    OBJECT_RELATIONS.put(OBJECT_SIZE_RELATION, SemType.newFuncSemType(OBJECT_TYPE_NAME, CanonicalNames.INT));
  }
  public static final Map<Value, SemType> BOX_RELATIONS = new HashMap<>();
  static {
    BOX_RELATIONS.put(OBJECT_IN_BOX_RELATION, SemType.newFuncSemType(BOX_TYPE_NAME, OBJECT_TYPE_NAME));
  }

  public static String getBoxName(int index) {return BOX_NAME_PREFIX + index; };

  public static String getObjectName(int index) {return OBJECT_NAME_PREFIX + index; };
}