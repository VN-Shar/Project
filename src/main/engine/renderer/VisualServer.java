package engine.renderer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import engine.node._2D.Frame;
import engine.node._2D.Sprite;
import engine.node._2D.Text;

public class VisualServer {

    private static final int MAX_BATCH_SIZE = 1024;
    private static final Comparator<RenderBatch> batchComparator = new Comparator<RenderBatch>() {

        public int compare(RenderBatch o1, RenderBatch o2) {
            return o1.getZIndex() - o2.getZIndex();
        };
    };

    private static List<RenderBatch> batches = new ArrayList<RenderBatch>();

    public static void render() {

        for (RenderBatch batch : batches) {
            batch.render();
        }
    }

    public static void draw(Sprite sprite) {
        for (RenderBatch batch : batches) {
            if (batch instanceof SpriteBatch) {
                if (batch.getZIndex() == sprite.getZIndex()) {
                    if (batch.hasRoom()) {
                        batch.draw(sprite);
                        return;
                    }
                }

                if (batch.getZIndex() > sprite.getZIndex()) {
                    break;
                }
            }
        }
        SpriteBatch batch = new SpriteBatch(sprite.getZIndex(), MAX_BATCH_SIZE);
        batches.add(batch);
        batch.draw(sprite);
        batches.sort(batchComparator);

    }

    public static void draw(Frame frame) {
        for (RenderBatch batch : batches) {

            if (batch instanceof FrameBatch) {

                if (batch.getZIndex() == frame.getZIndex()) {
                    if (batch.hasRoom()) {
                        batch.draw(frame);
                        return;
                    }
                }

                if (batch.getZIndex() > frame.getZIndex()) {
                    break;
                }
            }
        }
        FrameBatch batch = new FrameBatch(frame.getZIndex(), MAX_BATCH_SIZE);
        batches.add(batch);
        batch.draw(frame);
        batches.sort(batchComparator);
    }

    public static void draw(Text text) {
        for (RenderBatch batch : batches) {

            if (batch instanceof TextBatch) {

                if (batch.getZIndex() == text.getZIndex()) {
                    if (batch.hasRoom()) {
                        batch.draw(text);
                        return;
                    }
                }

                if (batch.getZIndex() > text.getZIndex()) {
                    break;
                }
            }
        }
        TextBatch batch = new TextBatch(text.getZIndex(), MAX_BATCH_SIZE);
        batches.add(batch);
        batch.draw(text);
        batches.sort(batchComparator);
    }
}
