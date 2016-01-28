package mate.pkg2016;

import net.java.games.input.Component;
import net.java.games.input.Component.*;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

class SimpleJoystick_Error extends Error {
    public SimpleJoystick_Error (String message) {
        super(message);
    }
}

class SimpleJoystick_Connection_Error extends SimpleJoystick_Error {
    public SimpleJoystick_Connection_Error (String message) {
        super(message);
    }
}

public class SimpleJoystick {
    private Controller joystick;
    private Component xAxis, yAxis, zAxis, slider, pov;
    
    public SimpleJoystick(String name) {
        Controller[] devices = ControllerEnvironment.getDefaultEnvironment().getControllers();
        
        for(int i = 0; i < devices.length && joystick == null; i++) {
            if(devices[i].getName().equals(name)) {
                joystick = devices[i];
            }
        }
        
        if(joystick == null) {
            throw new SimpleJoystick_Connection_Error("No connected device is called '" + name + "'.");
        } else {
            Component[] controls = joystick.getComponents();
            for (Component control : controls) {
                if (control.getIdentifier() == Component.Identifier.Axis.X) {
                    xAxis = control;
                }
                if (control.getIdentifier() == Component.Identifier.Axis.Y) {
                    yAxis = control;
                }
                if (control.getIdentifier() == Component.Identifier.Axis.Z) {
                    zAxis = control;
                }
                if (control.getIdentifier() == Component.Identifier.Axis.SLIDER) {
                    slider = control;
                }
                if (control.getIdentifier() == Component.Identifier.Axis.POV) {
                    pov = control;
                }
            }
        }
    }
    
    public void poll() {
        if(!joystick.poll()) {
            throw new SimpleJoystick_Connection_Error("The joystick appears to have been disconnected.");
        }
    }
    
    public float pov() {
        if(pov == null) {
            throw new Error("The joystick doesn't appear to have a POV.");
        } else {
            return pov.getPollData();
        }
    }
    
    public float X() {
        if(xAxis == null) {
            throw new Error("The joystick doesn't appear to have an x-axis.");
        } else {
            return xAxis.getPollData() * -1;
        }
    }
    
    public float Y() {
        if(yAxis == null) {
            throw new Error("The joystick doesn't appear to have an y-axis.");
        } else {
            return yAxis.getPollData() * -1;
        }
    }
    
    public float Z() {
        if(zAxis == null) {
            throw new Error("The joystick doesn't appear to have an z-axis.");
        } else {
            return zAxis.getPollData() * -1;
        }
    }
    
    public float slider() {
        if(slider == null) {
            throw new Error("The joystick doesn't appear to have a slider.");
        } else {
            return slider.getPollData() * -1;
        }
    }
}