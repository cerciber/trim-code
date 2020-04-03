package fasttask.controller.code;

import fasttask.controller.settting.SettingController;
import fasttask.data.system.FileAccess;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PythonController extends CodeController {

    static final String PYTHON_GENERATED_FILE = SettingController.getGeneratedFolder() + "\\PythonGenerated\\PythonGenerated.py";
    static final String PYTHON_GENERATED_DIRECTORY = SettingController.getGeneratedFolder() + "\\generated\\PythonGenerated";

    public PythonController(String direction) {
        super(direction);
    }
    
    @Override
    public String languaje() {
        return "Python";
    }
    
    @Override
    public Color color() {
        return new Color(120, 0, 140);
    }

    @Override
    public String classNameRE() {
        return "class[ ].*?(.*?)(?:[(]|[:])";
    }

    @Override
    public String descriptionRE() {
        return "('''(.*?)''')|(#(.*?)\n)";
    }

    @Override
    public String parametersRE() {
        return ".*?__init__[ ]*?[(](?:(?:.*?)),(.*?)[)]:";
    }

    @Override
    public void creteExecutable(String[] parameters) {
        
        // Crear codigo del ejecutable
        String parametersString = Arrays.toString(parameters);
        String generatedCode = FileAccess.loadContent(direction) + "\n" + className() + "(" + parametersString.substring(1, parametersString.length() - 1) + ")";

        // Generar ejecutable
        FileAccess.savedContent(PYTHON_GENERATED_FILE, generatedCode);
        
    }

    @Override
    public String runCommand() {
        return SettingController.getPythonFolder() + "\\python.exe \"" + new File(PYTHON_GENERATED_FILE).getAbsoluteFile().toString() + "\"";
    }

}