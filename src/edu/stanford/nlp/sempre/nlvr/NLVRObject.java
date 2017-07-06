package edu.stanford.nlp.sempre.nlvr;

import edu.stanford.nlp.sempre.NameValue;

import java.util.HashMap;
import java.util.Map;

/**
 * NLVRObject is a geometric shape (square, triangle or a circle).
 * This class contains fields that describe the object.
 *
 * Created by pdasigi on 6/26/17.
 */
public class NLVRObject {

  public enum Shape {CIRCLE, TRIANGLE, SQUARE}
  public enum Color {BLUE, BLACK, YELLOW}

  public static final Map<String, Color> STRING_TO_COLOR;
  public static final Map<String, Shape> STRING_TO_SHAPE;

  static {
    STRING_TO_COLOR = new HashMap<>();
    STRING_TO_COLOR.put("blue", Color.BLUE);
    STRING_TO_COLOR.put("black", Color.BLACK);
    STRING_TO_COLOR.put("yellow", Color.YELLOW);
    STRING_TO_SHAPE = new HashMap<>();
    STRING_TO_SHAPE.put("square", Shape.SQUARE);
    STRING_TO_SHAPE.put("triangle", Shape.TRIANGLE);
    STRING_TO_SHAPE.put("circle", Shape.CIRCLE);
  }

  public static final Map<Color, NameValue> COLOR_NAME_VALUES;
  public static final Map<Shape, NameValue> SHAPE_NAME_VALUES;

  static {
    COLOR_NAME_VALUES = new HashMap<>();
    COLOR_NAME_VALUES.put(Color.BLACK, new NameValue(NLVRTypeSystem.COLOR_NAME_PREFIX + ".black"));
    COLOR_NAME_VALUES.put(Color.YELLOW, new NameValue(NLVRTypeSystem.COLOR_NAME_PREFIX + ".yellow"));
    COLOR_NAME_VALUES.put(Color.BLUE, new NameValue(NLVRTypeSystem.COLOR_NAME_PREFIX + ".blue"));
    SHAPE_NAME_VALUES = new HashMap<>();
    SHAPE_NAME_VALUES.put(Shape.CIRCLE, new NameValue(NLVRTypeSystem.SHAPE_NAME_PREFIX + ".circle"));
    SHAPE_NAME_VALUES.put(Shape.SQUARE, new NameValue(NLVRTypeSystem.SHAPE_NAME_PREFIX + ".square"));
    SHAPE_NAME_VALUES.put(Shape.TRIANGLE, new NameValue(NLVRTypeSystem.SHAPE_NAME_PREFIX + ".triangle"));
  }

  public final int size;
  public final int xLoc;
  public final int yLoc;
  // Keep track of distances from right and bottom walls for questions that ask about
  // touching a wall. Distances from left and top walls are given by xLoc and yLoc respectively.
  public final int distanceToRight;
  public final int distanceToBottom;
  public final Shape shape;
  public final Color color;
  public final NameValue nameValue;

  public NLVRObject(int index, int size, String shape, String color, int xLoc, int yLoc) {
    this.size = size;
    this.shape = getShapeByName(shape);
    if (this.shape == null) {
      throw new RuntimeException("Unknown shape: "+ shape);
    }
    this.color = getColorByName(color);
    if (this.color == null) {
      throw new RuntimeException("Unknown color: "+ color);
    }
    this.xLoc = xLoc;
    this.yLoc = yLoc;
    this.distanceToRight = NLVRBox.BOXSIZE - (this.xLoc + this.size);
    this.distanceToBottom = NLVRBox.BOXSIZE - (this.yLoc + this.size);
    this.nameValue = new NameValue(NLVRTypeSystem.getObjectName(index));
  }

  public static Color getColorByName(String color) {
    color = color.toLowerCase();
    if (STRING_TO_COLOR.containsKey(color)) {
      return STRING_TO_COLOR.get(color);
    } else {
      return null;
    }
  }

  public static Shape getShapeByName(String shape) {
    shape = shape.toLowerCase();
    if (STRING_TO_SHAPE.containsKey(shape)) {
      return STRING_TO_SHAPE.get(shape);
    } else {
      return null;
    }
  }
}
