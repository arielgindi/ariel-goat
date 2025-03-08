package bit_transactions.dal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtils {
	
    private static final String FILE_PATH = "UserData.ser";

    public static boolean fileExists() {
        File file = new File(FILE_PATH);
        return file.exists() && file.isFile();
    }

    public static void serializeData(UserData ud) throws IOException{
		FileOutputStream fileOut = null;
		ObjectOutputStream out = null;
		try {
			// create the file
            File file = new File(FILE_PATH); 
            // open an output stream ( allows you to overwrite the file)
            fileOut = new FileOutputStream(file);
            // wrap the output stream in an object stream so we can write objects
            out = new ObjectOutputStream(fileOut);
            // write the objects in bytes to the file
            out.writeObject(ud);
        }
		finally {
			if (out != null) {
                out.close();
            }
            if (fileOut != null) {
                fileOut.close();
            }
		}
	}
	
	public static UserData deserializeData() throws ClassNotFoundException, IOException{
		UserData deserializedData = null;
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		try {
			// open an input stream to the file
			fileIn = new FileInputStream(FILE_PATH);
			// wrap the input stream in an object stream so we can read an object
	    	in = new ObjectInputStream(fileIn);
	    	// read the obj and cast to user data
	    	deserializedData = (UserData) in.readObject();
		}
		finally {
			if (fileIn != null) {
				fileIn.close();
            }
            if (in != null) {
                in.close();
            }
		}
		return deserializedData;
	}
	
}

