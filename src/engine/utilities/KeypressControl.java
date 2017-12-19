package engine.utilities;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeypressControl {
    private KeyListener keyListener;
    private List<Integer> keyPressedList = new ArrayList<Integer>();

    public KeypressControl() {
        keyListener = new KeyAdapter() {

            public void keyPressed(KeyEvent key) {
                keyPressedList.add(key.getKeyCode());
            }

            public void keyReleased(KeyEvent key) {
                removeKey(key.getKeyCode());
            }
        };
    }

    public void removeKey(int keyCode) {
        int index = 0;
        while (keyExists(keyCode)) {
            if(keyPressedList.get(index) == keyCode) {
                keyPressedList.remove(keyPressedList.get(index));
            } else {
                index++;
            }
        }
    }

    public boolean keyExists(int keyCode) {
        for(int key : keyPressedList) {
            if(key == keyCode) {
                return true;
            }
        }
        return false;
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }
}
