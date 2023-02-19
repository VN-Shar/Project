package engine.util;

import org.lwjgl.*;
import org.lwjgl.stb.*;
import org.lwjgl.system.*;

import java.io.IOException;
import java.nio.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryStack.*;

import static engine.util.IOUtil.*;

public class Font {

    private final String filePath;
    private ByteBuffer ttf;

    private STBTTFontinfo fontInfo;

    private int ascent;
    private int descent;
    private int lineGap;

    private STBTTBakedChar.Buffer charData;
    private int bitmapWidth, bitmapHeight;
    private int textureID;
    private int fontHeight = 24;

    public Font(String filePath, int fontHeight, int bitmapWidth, int bitmapHeight) {
        this.filePath = filePath;
        this.fontHeight = fontHeight;
        this.bitmapWidth = bitmapWidth;
        this.bitmapHeight = bitmapHeight;

        try {
            ttf = resourceToByteBuffer(filePath, 1024 * 1024);

            fontInfo = STBTTFontinfo.create();
            if (!stbtt_InitFont(fontInfo, ttf))
                throw new IllegalStateException("Failed to initialize font information.");

            try (MemoryStack fontStack = stackPush()) {
                IntBuffer pAscent = fontStack.mallocInt(1);
                IntBuffer pDescent = fontStack.mallocInt(1);
                IntBuffer pLineGap = fontStack.mallocInt(1);

                stbtt_GetFontVMetrics(fontInfo, pAscent, pDescent, pLineGap);

                ascent = pAscent.get(0);
                descent = pDescent.get(0);
                lineGap = pLineGap.get(0);

            }
            init(bitmapWidth, bitmapHeight, fontHeight);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(int bitmapWidth, int bitmapHeight, int fontHeight) {

        textureID = glGenTextures();
        charData = STBTTBakedChar.malloc(96);
        
        ByteBuffer bitmap = BufferUtils.createByteBuffer(bitmapWidth * bitmapHeight);
        stbtt_BakeFontBitmap(ttf, fontHeight, bitmap, bitmapWidth, bitmapHeight, 32, charData);
        
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, bitmapWidth, bitmapHeight, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public String getFileName() {
        return this.filePath;
    }

    public float getAscent() {
        return this.ascent;
    }

    public float getDescent() {
        return this.descent;
    }

    public float getLineGap() {
        return this.lineGap;
    }

    public int getFontHeight() {
        return this.fontHeight;
    }

    public void setFontHeight(int height) {
        this.fontHeight = height;
        init(bitmapWidth, bitmapHeight, fontHeight);
    }

    public STBTTFontinfo getFontInfo() {
        return this.fontInfo;
    }

    public STBTTBakedChar.Buffer getCharData() {
        return charData;
    }

    public int getBitmapWidth() {
        return this.bitmapWidth;
    }

    public int getBitmapHeight() {
        return this.bitmapHeight;
    }

    public int getTextureID() {
        return this.textureID;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
