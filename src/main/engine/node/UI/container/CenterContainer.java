package engine.node.UI.container;

import org.joml.Vector2f;

import engine.node.Node;
import engine.node.Node2D;
import engine.node.FlagType.SizeFlag;

public class CenterContainer extends Container {

    @Override
    public void resize() {
        for (Node c : getChildren()) {
            // Skip node that is not inherit from Node2D
            if (!(c instanceof Node2D))
                continue;

            Node2D child = (Node2D) c;

            // Center the child

            if (child.getSizeFlag() == SizeFlag.EXPAND) {
                child.getTransform().setSize(getTransform().getSize());
            }

            Vector2f size = new Vector2f(getTransform().getSize()).add(child.getTransform().getSize());
            child.getTransform().setPosition(size.div(2));

        }
    }
}
