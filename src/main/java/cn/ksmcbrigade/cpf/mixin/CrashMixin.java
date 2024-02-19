package cn.ksmcbrigade.cpf.mixin;

import cn.ksmcbrigade.cpf.CrashPipe_Forge;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.sound.sampled.*;
import java.io.File;

@Mixin(Minecraft.class)
public abstract class CrashMixin {

    @Inject(method = "crash",at = @At("HEAD"))
    private static void crash(CrashReport p_91333_, CallbackInfo ci){
        try {
            long start = System.currentTimeMillis();

            File file = new File("config/crash-pipe/crash.wav");
            if(file.exists()){
                AudioInputStream as;
                as = AudioSystem.getAudioInputStream(file);
                AudioFormat format = as.getFormat();
                SourceDataLine sdl = null;
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                sdl = (SourceDataLine) AudioSystem.getLine(info);
                sdl.open(format);
                sdl.start();
                int nBytesRead = 0;
                byte[] abData = new byte[512];
                while (nBytesRead != -1) {
                    nBytesRead = as.read(abData, 0, abData.length);
                    if (nBytesRead >= 0)
                        sdl.write(abData, 0, nBytesRead);
                }
                sdl.drain();
                sdl.close();
            }

            CrashPipe_Forge.LOGGER.info("Sound use: "+(System.currentTimeMillis()-start)/1000 + " s");
        }
        catch (Exception e){
            CrashPipe_Forge.LOGGER.error("Failed to play the crash sound.");
            e.printStackTrace();
        }
    }
}
