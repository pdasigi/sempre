package edu.stanford.nlp.sempre.nlvr;

import edu.stanford.nlp.sempre.NameValue;

import java.util.ArrayList;
import java.util.List;

/**
 * NLVRBox is one of the three panels in the context of an NLVR example.
 * Each box contains some NLVRObjects.
 *
 * Created by pdasigi on 6/26/17.
 */
public class NLVRBox {
    // Boxes are 100x100 squares.
    public static final int BOXSIZE = 100;
    public final NameValue nameValue;
    public final List<NLVRObject> children;

    public NLVRBox(int index) {
        this.children = new ArrayList<>();
        this.nameValue = new NameValue(NLVRTypeSystem.getBoxName(index));
    }
}
