// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.dev.ss.lib;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.dev.ss.screen.GameScreen;

public class Img {
    public float height;
    protected String[] name;
    public int nowIdx;
    public String nowName;
    protected TextureRegion[] textureRegionArray;
    public boolean visible;
    public float width;
    public float x;
    public float y;

    public Img(TextureRegion[] _textureRegionArray) {
        super();
        this.width = 0f;
        this.height = 0f;
        this.x = 0f;
        this.y = 0f;
        this.visible = true;
        this.nowIdx = 0;
        this.nowName = "";
        this.textureRegionArray = _textureRegionArray;
    }

    public Img() {
        super();
        this.width = 0f;
        this.height = 0f;
        this.x = 0f;
        this.y = 0f;
        this.visible = true;
        this.nowIdx = 0;
        this.nowName = "";
    }

    public void draw() {
        if(this.visible) {
            GameScreen.batch.draw(this.textureRegionArray[this.nowIdx], this.x, this.y, this.width, this.height);
        }
    }

    public void draw(SpriteBatch batch) {
        if(this.visible) {
            batch.draw(this.textureRegionArray[this.nowIdx], this.x, this.y, this.width, this.height);
        }
    }

    public Rectangle getBoundingRectangle() {
        Rectangle v0 = new Rectangle();
        v0.x = this.x;
        v0.y = this.y;
        v0.width = this.width;
        v0.height = this.height;
        return v0;
    }

    protected int getImageIdx(String _name) {
        int v1 = -1;
        int v0 = 0;
        while(v0 < this.name.length) {
            if(_name.equals(this.name[v0])) {
                v1 = v0;
            }
            else {
                ++v0;
                continue;
            }

            break;
        }

        return v1;
    }

    public void setImage(String _name) {
        this.nowIdx = this.getImageIdx(_name);
        this.nowName = _name;
    }

    public void setImage(int _imageIdx) {
        this.nowIdx = _imageIdx;
        this.nowName = this.name[_imageIdx];
    }

    public void setNames(String[] _name) {
        this.name = new String[_name.length];
        int v0;
        for(v0 = 0; v0 < _name.length; ++v0) {
            this.name[v0] = _name[v0];
        }
    }

    public void setPosition(float _x, float _y) {
        this.x = _x;
        this.y = _y;
    }

    public void setSize(float _width, float _height) {
        this.width = _width;
        this.height = _height;
    }
}

