/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Zeno410Utils;

import net.minecraft.entity.player.EntityPlayer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Zeno410
 */
public class PlayerID {

    private final String name;

    private PlayerID(String _name) {
        name = _name;
    }

    public PlayerID(EntityPlayer player) {
        this(player.getDisplayName());
    }

    public String getName() {
        return name;
    }

    public static class PlayerIDStreamer extends Zeno410Utils.Streamer<PlayerID> {

        public PlayerID readFrom(DataInput input) throws IOException {
            String result = input.readUTF();
            return new PlayerID(result);
        }

        public void writeTo(PlayerID written, DataOutput output) throws IOException {
            output.writeUTF(written.name);

        }
    }

    // convenience function for player specific things
    public <Type> PlayerSpecific<Type> specific(Type type) {
        return new PlayerSpecific<Type>(this, type);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object compared) {
        if (compared == null) return false;
        if (compared instanceof PlayerID) {
            return ((PlayerID) compared).name.equals(name);
        }
        return false;
    }


}
