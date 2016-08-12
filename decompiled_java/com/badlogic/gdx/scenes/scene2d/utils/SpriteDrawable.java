// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas$AtlasSprite;

public class SpriteDrawable extends BaseDrawable implements TransformDrawable {
    private Sprite sprite;
    private static Color tmpColor;

    static  {
        SpriteDrawable.tmpColor = new Color();
    }

    public SpriteDrawable(Sprite sprite) {
        super();
        this.setSprite(sprite);
    }

    public SpriteDrawable() {
        super();
    }

    public SpriteDrawable(SpriteDrawable drawable) {
        super(((Drawable)drawable));
        this.setSprite(drawable.sprite);
    }

    public void draw(Batch batch, float x, float y, float width, float height) {
        this.draw(batch, x, y, width / 2f, height / 2f, width, height, 1f, 1f, 0f);
    }

    public void draw(Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        this.sprite.setOrigin(originX, originY);
        this.sprite.setRotation(rotation);
        this.sprite.setScale(scaleX, scaleY);
        this.sprite.setBounds(x, y, width, height);
        Color v0 = this.sprite.getColor();
        this.sprite.setColor(SpriteDrawable.tmpColor.set(v0).mul(batch.getColor()));
        this.sprite.draw(batch);
        this.sprite.setColor(v0);
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.setMinWidth(sprite.getWidth());
        this.setMinHeight(sprite.getHeight());
    }

    public SpriteDrawable tint(Color tint) {
        AtlasSprite v1_1;
        SpriteDrawable v0 = new SpriteDrawable(this);
        Sprite v1 = v0.getSprite();
        if((v1 instanceof AtlasSprite)) {
            v1_1 = new AtlasSprite(((AtlasSprite)v1));
        }
        else {
            v1 = new Sprite(v1);
        }

        ((Sprite)v1_1).setColor(tint);
        v0.setSprite(((Sprite)v1_1));
        return v0;
    }
}

