package cn.ksmcbrigade.cpf;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.*;
import java.util.concurrent.CountDownLatch;

@Mod("cpf")
public class CrashPipe_Forge {

    public static final Logger LOGGER = LogManager.getLogger();

    public CrashPipe_Forge() throws IOException {
        MinecraftForge.EVENT_BUS.register(this);
        File dir = new File("config/crash-pipe");
        File wav = new File("config/crash-pipe/crash.wav");
        if(!dir.exists()){
            dir.mkdirs();
        }
        if(!wav.exists()){
            extractFile("crash.wav", wav.getPath());
        }
        LOGGER.info("Crash Pipe-Forge mod loaded.");
    }

    public static void extractFile(String resourceName, String destinationPath) throws FileNotFoundException {
        ClassLoader classLoader = CrashPipe_Forge.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(resourceName);
        if (inputStream == null) {
            throw new FileNotFoundException(resourceName);
        }
        try {
            File destinationFile = new File(destinationPath);
            FileOutputStream outputStream = new FileOutputStream(destinationFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
