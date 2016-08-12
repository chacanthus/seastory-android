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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TiledDrawable extends TextureRegionDrawable {
    public TiledDrawable() {
        super();
    }

    public TiledDrawable(TextureRegion region) {
        super(region);
    }

    public TiledDrawable(TextureRegionDrawable drawable) {
        super(drawable);
    }

    public void draw(Batch batch, float x, float y, float width, float height) {
        float v17;
        float v16;
        TextureRegion v3 = this.getRegion();
        float v6 = ((float)v3.getRegionWidth());
        float v7 = ((float)v3.getRegionHeight());
        int v30 = ((int)(width / v6));
        int v31 = ((int)(height / v7));
        float v12 = width - (((float)v30)) * v6;
        float v23 = height - (((float)v31)) * v7;
        float v34 = x;
        float v35 = y;
        int v32;
        for(v32 = 0; v32 < v30; ++v32) {
            y = v35;
            int v33;
            for(v33 = 0; v33 < v31; ++v33) {
                batch.draw(v3, x, y, v6, v7);
                y += v7;
            }

            x += v6;
        }

        Texture v9 = v3.getTexture();
        float v14 = v3.getU();
        float v15 = v3.getV2();
        if(v12 > 0f) {
            v16 = v14 + v12 / (((float)v9.getWidth()));
            v17 = v3.getV();
            y = v35;
            for(v33 = 0; v33 < v31; ++v33) {
                batch.draw(v9, x, y, v12, v7, v14, v15, v16, v17);
                y += v7;
            }

            if(v23 > 0f) {
                batch.draw(v9, x, y, v12, v23, v14, v15, v16, v15 - v23 / (((float)v9.getHeight())));
            }
        }

        if(v23 > 0f) {
            v16 = v3.getU2();
            v17 = v15 - v23 / (((float)v9.getHeight()));
            x = v34;
            for(v32 = 0; v32 < v30; ++v32) {
                batch.draw(v9, x, y, v6, v23, v14, v15, v16, v17);
                x += v6;
            }
        }
    }
}

