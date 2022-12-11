package com.tcc.aventurafelina.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class TextStyleManager {

    private Label.LabelStyle fontTitle, font;
    private TextField.TextFieldStyle textFieldStyle;
    private Texture background, emptyBackground;

    public TextStyleManager() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/I-pixel-u.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.borderColor = Color.BLACK;

        //Title font params
        fontParameter.size = 60;
        fontParameter.borderWidth = 6;

        fontTitle = new Label.LabelStyle(fontGenerator.generateFont(fontParameter), Color.WHITE);

        //Normal font params
        fontParameter.size = 24;
        fontParameter.borderWidth = 3;

        font = new Label.LabelStyle(fontGenerator.generateFont(fontParameter), Color.WHITE);

        textFieldStyle = new TextField.TextFieldStyle(fontGenerator.generateFont(fontParameter), Color.WHITE, null, null, null);

        background = new Texture("images/wallpaper.png");
        emptyBackground = new Texture("images/empty.png");
    }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public Label.LabelStyle getFontTitle() {
        return fontTitle;
    }

    public void setFontTitle(Label.LabelStyle fontTitle) {
        this.fontTitle = fontTitle;
    }

    public Label.LabelStyle getFont() {
        return font;
    }

    public void setFont(Label.LabelStyle font) {
        this.font = font;
    }

    public TextField.TextFieldStyle getTextFieldStyle() {
        return textFieldStyle;
    }

    public void setTextFieldStyle(TextField.TextFieldStyle textFieldStyle) {
        this.textFieldStyle = textFieldStyle;
    }

    public Texture getBackground() {
        return background;
    }

    public void setBackground(Texture background) {
        this.background = background;
    }

    public Texture getEmptyBackground() {
        return emptyBackground;
    }

    public void setEmptyBackground(Texture emptyBackground) {
        this.emptyBackground = emptyBackground;
    }

    // </editor-fold>
}
