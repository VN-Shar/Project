package engine.node.UI;

public class Color {

    public static enum Colors {

        ALICE_BLUE(0.941176F, 0.972549F, 1F, 1F),

        ANTIQUE_WHITE(0.980392F, 0.921569F, 0.843137F, 1F),

        AQUA(0F, 1F, 1F, 1F),

        AQUAMARINE(0.498039f, 1f, 0.831373f, 1),

        AZURE(0.941176f, 1f, 1f, 1),

        BEIGE(0.960784f, 0.960784f, 0.862745f, 1),

        BISQUE(1f, 0.894118f, 0.768627f, 1),

        BLACK(0f, 0f, 0f, 1),

        BLANCHED_ALMOND(1f, 0.921569f, 0.803922f, 1),

        BLUE(0f, 0f, 1f, 1),

        BLUE_VIOLET(0.541176f, 0.168627f, 0.886275f, 1),

        BROWN(0.647059f, 0.164706f, 0.164706f, 1),

        BURLY_WOOD(0.870588f, 0.721569f, 0.529412f, 1),

        CADET_BLUE(0.372549f, 0.619608f, 0.627451f, 1),

        CHARTREUSE(0.498039f, 1f, 0f, 1),

        CHOCOLATE(0.823529f, 0.411765f, 0.117647f, 1),

        CORAL(1f, 0.498039f, 0.313726f, 1),

        CORNFLOWER(0.392157f, 0.584314f, 0.929412f, 1),

        CORN_SILK(1f, 0.972549f, 0.862745f, 1),

        CRIMSON(0.862745f, 0.0784314f, 0.235294f, 1),

        CYAN(0f, 1f, 1f, 1),

        DARKBLUE(0f, 0f, 0.545098f, 1),

        DARK_CYAN(0f, 0.545098f, 0.545098f, 1),

        DARK_GOLDENROD(0.721569f, 0.52549f, 0.0431373f, 1),

        DARK_GRAY(0.662745f, 0.662745f, 0.662745f, 1),

        DARK_GREEN(0f, 0.392157f, 0f, 1),

        DARK_KHAKI(0.741176f, 0.717647f, 0.419608f, 1),

        DARK_MAGENTA(0.545098f, 0f, 0.545098f, 1),

        DARK_OLIVE_GREEN(0.333333f, 0.419608f, 0.184314f, 1),

        DARK_ORANGE(1f, 0.54902f, 0f, 1),

        DARK_ORCHID(0.6f, 0.196078f, 0.8f, 1),

        DARK_RED(0.545098f, 0f, 0f, 1),

        DARK_SALMON(0.913725f, 0.588235f, 0.478431f, 1),

        DARK_SEA_GREEN(0.560784f, 0.737255f, 0.560784f, 1),

        DARK_SLATE_BLUE(0.282353f, 0.239216f, 0.545098f, 1),

        DARK_SLATE_GRAY(0.184314f, 0.309804f, 0.309804f, 1),

        DARK_TURQUOISE(0f, 0.807843f, 0.819608f, 1),

        DARK_VIOLET(0.580392f, 0f, 0.827451f, 1),

        DEEP_PINK(1f, 0.0784314f, 0.576471f, 1),

        DEEP_SKY_BLUE(0f, 0.74902f, 1f, 1),

        DIM_GRAY(0.411765f, 0.411765f, 0.411765f, 1),

        DODGER_BLUE(0.117647f, 0.564706f, 1f, 1),

        FIREBRICK(0.698039f, 0.133333f, 0.133333f, 1),

        FLORAL_WHITE(1f, 0.980392f, 0.941176f, 1),

        FOREST_GREEN(0.133333f, 0.545098f, 0.133333f, 1),

        FUCHSIA(1f, 0f, 1f, 1),

        GAINSBORO(0.862745f, 0.862745f, 0.862745f, 1),

        GHOST_WHITE(0.972549f, 0.972549f, 1f, 1),

        GOLD(1f, 0.843137f, 0f, 1),

        GOLDENROD(0.854902f, 0.647059f, 0.12549f, 1),

        GRAY(0.745098f, 0.745098f, 0.745098f, 1),

        GREEN(0f, 1f, 0f, 1),

        GREEN_YELLOW(0.678431f, 1f, 0.184314f, 1),

        HONEYDEW(0.941176f, 1f, 0.941176f, 1),

        HOT_PINK(1f, 0.411765f, 0.705882f, 1),

        INDIAN_RED(0.803922f, 0.360784f, 0.360784f, 1),

        INDIGO(0.294118f, 0f, 0.509804f, 1),

        IVORY(1f, 1f, 0.941176f, 1),

        KHAKI(0.941176f, 0.901961f, 0.54902f, 1),

        LAVENDER(0.901961f, 0.901961f, 0.980392f, 1),

        LAVENDER_BLUSH(1f, 0.941176f, 0.960784f, 1),

        LAWN_GREEN(0.486275f, 0.988235f, 0f, 1),

        LEMON_CHIFFON(1f, 0.980392f, 0.803922f, 1),

        LIGHTBLUE(0.678431f, 0.847059f, 0.901961f, 1),

        LIGHT_CORAL(0.941176f, 0.501961f, 0.501961f, 1),

        LIGHT_CYAN(0.878431f, 1f, 1f, 1),

        LIGHT_GOLDENROD(0.980392f, 0.980392f, 0.823529f, 1),

        LIGHT_GRAY(0.827451f, 0.827451f, 0.827451f, 1),

        LIGHT_GREEN(0.564706f, 0.933333f, 0.564706f, 1),

        LIGHT_PINK(1f, 0.713726f, 0.756863f, 1),

        LIGHT_SALMON(1f, 0.627451f, 0.478431f, 1),

        LIGHT_SEA_GREEN(0.12549f, 0.698039f, 0.666667f, 1),

        LIGHT_SKY_BLUE(0.529412f, 0.807843f, 0.980392f, 1),

        LIGHT_SLATE_GRAY(0.466667f, 0.533333f, 0.6f, 1),

        LIGHT_STEEL_BLUE(0.690196f, 0.768627f, 0.870588f, 1),

        LIGHT_YELLOW(1f, 1f, 0.878431f, 1),

        LIME(0f, 1f, 0f, 1),

        LIME_GREEN(0.196078f, 0.803922f, 0.196078f, 1),

        LINEN(0.980392f, 0.941176f, 0.901961f, 1),

        MAGENTA(1f, 0f, 1f, 1),

        MAROON(0.690196f, 0.188235f, 0.376471f, 1),

        MEDIUM_AQUAMARINE(0.4f, 0.803922f, 0.666667f, 1),

        MEDIUM_BLUE(0f, 0f, 0.803922f, 1),

        MEDIUM_ORCHID(0.729412f, 0.333333f, 0.827451f, 1),

        MEDIUM_PURPLE(0.576471f, 0.439216f, 0.858824f, 1),

        MEDIUM_SEA_GREEN(0.235294f, 0.701961f, 0.443137f, 1),

        MEDIUM_SLATE_BLUE(0.482353f, 0.407843f, 0.933333f, 1),

        MEDIUM_SPRING_GREEN(0f, 0.980392f, 0.603922f, 1),

        MEDIUM_TURQUOISE(0.282353f, 0.819608f, 0.8f, 1),

        MEDIUM_VIOLET_RED(0.780392f, 0.0823529f, 0.521569f, 1),

        MIDNIGHT_BLUE(0.0980392f, 0.0980392f, 0.439216f, 1),

        MINT_CREAM(0.960784f, 1f, 0.980392f, 1),

        MISTY_ROSE(1f, 0.894118f, 0.882353f, 1),

        MOCCASIN(1f, 0.894118f, 0.709804f, 1),

        NAVAJO_WHITE(1f, 0.870588f, 0.678431f, 1),

        NAVY_BLUE(0f, 0f, 0.501961f, 1),

        OLD_LACE(0.992157f, 0.960784f, 0.901961f, 1),

        OLIVE(0.501961f, 0.501961f, 0f, 1),

        OLIVE_DRAB(0.419608f, 0.556863f, 0.137255f, 1),

        ORANGE(1f, 0.647059f, 0f, 1),

        ORANGE_RED(1f, 0.270588f, 0f, 1),

        ORCHID(0.854902f, 0.439216f, 0.839216f, 1),

        PALE_GOLDENROD(0.933333f, 0.909804f, 0.666667f, 1),

        PALE_GREEN(0.596078f, 0.984314f, 0.596078f, 1),

        PALE_TURQUOISE(0.686275f, 0.933333f, 0.933333f, 1),

        PALE_VIOLET_RED(0.858824f, 0.439216f, 0.576471f, 1),

        PAPAYA_WHIP(1f, 0.937255f, 0.835294f, 1),

        PEACH_PUFF(1f, 0.854902f, 0.72549f, 1),

        PERU(0.803922f, 0.521569f, 0.247059f, 1),

        PINK(1f, 0.752941f, 0.796078f, 1),

        PLUM(0.866667f, 0.627451f, 0.866667f, 1),

        POWDER_BLUE(0.690196f, 0.878431f, 0.901961f, 1),

        PURPLE(0.627451f, 0.12549f, 0.941176f, 1),

        REBECCA_PURPLE(0.4f, 0.2f, 0.6f, 1),

        RED(1f, 0f, 0f, 1),

        ROSY_BROWN(0.737255f, 0.560784f, 0.560784f, 1),

        ROYAL_BLUE(0.254902f, 0.411765f, 0.882353f, 1),

        SADDLE_BROWN(0.545098f, 0.270588f, 0.0745098f, 1),

        SALMON(0.980392f, 0.501961f, 0.447059f, 1),

        SANDY_BROWN(0.956863f, 0.643137f, 0.376471f, 1),

        SEA_GREEN(0.180392f, 0.545098f, 0.341176f, 1),

        SEASHELL(1f, 0.960784f, 0.933333f, 1),

        SIENNA(0.627451f, 0.321569f, 0.176471f, 1),

        SILVER(0.752941f, 0.752941f, 0.752941f, 1),

        SKY_BLUE(0.529412f, 0.807843f, 0.921569f, 1),

        SLATE_BLUE(0.415686f, 0.352941f, 0.803922f, 1),

        SLATE_GRAY(0.439216f, 0.501961f, 0.564706f, 1),

        SNOW(1f, 0.980392f, 0.980392f, 1),

        SPRING_GREEN(0f, 1f, 0.498039f, 1),

        STEEL_BLUE(0.27451f, 0.509804f, 0.705882f, 1),

        TAN(0.823529f, 0.705882f, 0.54902f, 1),

        TEAL(0f, 0.501961f, 0.501961f, 1),

        THISTLE(0.847059f, 0.74902f, 0.847059f, 1),

        TOMATO(1f, 0.388235f, 0.278431f, 1),

        TRANSPARENT(1f, 1f, 1f, 0),

        TURQUOISE(0.25098f, 0.878431f, 0.815686f, 1),

        VIOLET(0.933333f, 0.509804f, 0.933333f, 1),

        WEB_GRAY(0.501961f, 0.501961f, 0.501961f, 1),

        WEB_GREEN(0f, 0.501961f, 0f, 1),

        WEB_MAROON(0.501961f, 0f, 0f, 1),

        WEB_PURPLE(0.501961f, 0f, 0.501961f, 1),

        WHEAT(0.960784f, 0.870588f, 0.701961f, 1),

        WHITE(1f, 1f, 1f, 1),

        WHITE_SMOKE(0.960784f, 0.960784f, 0.960784f, 1),

        YELLOW(1f, 1f, 0f, 1),

        YELLOW_GREEN(0.603922f, 0.803922f, 0.196078f, 1);

        private float r, g, b, a;

        Colors(float r, float b, float g, float a) {
            this.r = r;
            this.b = b;
            this.g = g;
            this.a = a;
        }
    }

    public float r, g, b, a;

    public Color(float r, float b, float g, float a) {
        this.r = r;
        this.b = b;
        this.g = g;
        this.a = a;
    }

    public Color(Colors c) {
        this.a = c.a;
        this.r = c.r;
        this.g = c.g;
        this.b = c.b;
    }

    public Color lerp(Color target, float value) {
        return new Color(//
                r + (target.r - r) * value, //
                g + (target.g - g) * value, //
                b + (target.b - b) * value, //
                a + (target.a - a) * value);
    }

    @Override
    public String toString() {
        return "color: (" + r + ", " + g + ", " + b + ", " + a + ")";
    }
}
