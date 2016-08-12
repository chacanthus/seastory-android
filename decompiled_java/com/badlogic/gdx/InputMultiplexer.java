// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx;

import com.badlogic.gdx.utils.Array;

public class InputMultiplexer implements InputProcessor {
    private Array processors;

    public InputMultiplexer() {
        super();
        this.processors = new Array(4);
    }

    public InputMultiplexer(InputProcessor[] processors) {
        super();
        this.processors = new Array(4);
        int v0;
        for(v0 = 0; v0 < processors.length; ++v0) {
            this.processors.add(processors[v0]);
        }
    }

    public void addProcessor(int index, InputProcessor processor) {
        if(processor == null) {
            throw new NullPointerException("processor cannot be null");
        }

        this.processors.insert(index, processor);
    }

    public void addProcessor(InputProcessor processor) {
        if(processor == null) {
            throw new NullPointerException("processor cannot be null");
        }

        this.processors.add(processor);
    }

    public void clear() {
        this.processors.clear();
    }

    public Array getProcessors() {
        return this.processors;
    }

    public boolean keyDown(int keycode) {
        boolean v2;
        int v0 = 0;
        int v1 = this.processors.size;
        while(true) {
            if(v0 >= v1) {
                break;
            }
            else if(this.processors.get(v0).keyDown(keycode)) {
                v2 = true;
            }
            else {
                ++v0;
                continue;
            }

            goto label_9;
        }

        v2 = false;
    label_9:
        return v2;
    }

    public boolean keyTyped(char character) {
        boolean v2;
        int v0 = 0;
        int v1 = this.processors.size;
        while(true) {
            if(v0 >= v1) {
                break;
            }
            else if(this.processors.get(v0).keyTyped(character)) {
                v2 = true;
            }
            else {
                ++v0;
                continue;
            }

            goto label_9;
        }

        v2 = false;
    label_9:
        return v2;
    }

    public boolean keyUp(int keycode) {
        boolean v2;
        int v0 = 0;
        int v1 = this.processors.size;
        while(true) {
            if(v0 >= v1) {
                break;
            }
            else if(this.processors.get(v0).keyUp(keycode)) {
                v2 = true;
            }
            else {
                ++v0;
                continue;
            }

            goto label_9;
        }

        v2 = false;
    label_9:
        return v2;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        boolean v2;
        int v0 = 0;
        int v1 = this.processors.size;
        while(true) {
            if(v0 >= v1) {
                break;
            }
            else if(this.processors.get(v0).mouseMoved(screenX, screenY)) {
                v2 = true;
            }
            else {
                ++v0;
                continue;
            }

            goto label_9;
        }

        v2 = false;
    label_9:
        return v2;
    }

    public void removeProcessor(int index) {
        this.processors.removeIndex(index);
    }

    public void removeProcessor(InputProcessor processor) {
        this.processors.removeValue(processor, true);
    }

    public boolean scrolled(int amount) {
        boolean v2;
        int v0 = 0;
        int v1 = this.processors.size;
        while(true) {
            if(v0 >= v1) {
                break;
            }
            else if(this.processors.get(v0).scrolled(amount)) {
                v2 = true;
            }
            else {
                ++v0;
                continue;
            }

            goto label_9;
        }

        v2 = false;
    label_9:
        return v2;
    }

    public void setProcessors(Array arg1) {
        this.processors = arg1;
    }

    public int size() {
        return this.processors.size;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean v2;
        int v0 = 0;
        int v1 = this.processors.size;
        while(true) {
            if(v0 >= v1) {
                break;
            }
            else if(this.processors.get(v0).touchDown(screenX, screenY, pointer, button)) {
                v2 = true;
            }
            else {
                ++v0;
                continue;
            }

            goto label_9;
        }

        v2 = false;
    label_9:
        return v2;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean v2;
        int v0 = 0;
        int v1 = this.processors.size;
        while(true) {
            if(v0 >= v1) {
                break;
            }
            else if(this.processors.get(v0).touchDragged(screenX, screenY, pointer)) {
                v2 = true;
            }
            else {
                ++v0;
                continue;
            }

            goto label_9;
        }

        v2 = false;
    label_9:
        return v2;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean v2;
        int v0 = 0;
        int v1 = this.processors.size;
        while(true) {
            if(v0 >= v1) {
                break;
            }
            else if(this.processors.get(v0).touchUp(screenX, screenY, pointer, button)) {
                v2 = true;
            }
            else {
                ++v0;
                continue;
            }

            goto label_9;
        }

        v2 = false;
    label_9:
        return v2;
    }
}

