/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Zeno410Utils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Zeno410
 */
public interface Streamable {

    void readFrom(DataInput input) throws IOException;

    void writeTo(DataOutput output) throws IOException;
}
