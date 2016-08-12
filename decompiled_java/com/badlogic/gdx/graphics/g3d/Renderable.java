// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Matrix4;

public class Renderable {
    public Matrix4[] bones;
    public Environment environment;
    public Material material;
    public Mesh mesh;
    public int meshPartOffset;
    public int meshPartSize;
    public int primitiveType;
    public Shader shader;
    public Object userData;
    public final Matrix4 worldTransform;

    public Renderable() {
        super();
        this.worldTransform = new Matrix4();
    }

    public Renderable set(Renderable renderable) {
        this.worldTransform.set(renderable.worldTransform);
        this.material = renderable.material;
        this.mesh = renderable.mesh;
        this.meshPartOffset = renderable.meshPartOffset;
        this.meshPartSize = renderable.meshPartSize;
        this.primitiveType = renderable.primitiveType;
        this.bones = renderable.bones;
        this.environment = renderable.environment;
        this.shader = renderable.shader;
        this.userData = renderable.userData;
        return this;
    }
}

