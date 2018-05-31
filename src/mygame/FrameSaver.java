/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.post.SceneProcessor;
import com.jme3.profile.AppProfiler;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image;
import com.jme3.util.BufferUtils;
import com.jme3.util.Screenshots;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

/**
 *
 * @author sunlu
 */
public class FrameSaver implements SceneProcessor {
    
    ByteBuffer byteBuffer;
    BufferedImage rawFrame;
    RenderManager renderManager;
    boolean is_round2;
    
    boolean isInitialized = false;
    
    public FrameSaver(boolean is_round2) {
        this.is_round2 = is_round2;
    }

    @Override
    public void initialize(RenderManager rm, ViewPort vp) {
        Camera camera = vp.getCamera();
        int width = camera.getWidth();
        int height = camera.getHeight();
        byteBuffer = BufferUtils.createByteBuffer(width * height * 4);
        rawFrame = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        renderManager = rm;
        
        isInitialized = true;
    }

    @Override
    public void reshape(ViewPort vp, int w, int h) {}

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void preFrame(float tpf) {}

    @Override
    public void postQueue(RenderQueue rq) {}

    @Override
    public void postFrame(FrameBuffer out) {
        if (is_round2) {
            byteBuffer.clear();
            renderManager.getRenderer().readFrameBufferWithFormat(out, byteBuffer, Image.Format.BGRA8);
            Screenshots.convertScreenShot(byteBuffer, rawFrame);
            try {
                ImageIO.write(rawFrame, "png", new File("frame42.png"));
            } catch (IOException e) {
                System.err.println("Caught IOException: " + e.getMessage());
            }
        }
    }

    @Override
    public void cleanup() {}

    @Override
    public void setProfiler(AppProfiler profiler) {}
    
}
