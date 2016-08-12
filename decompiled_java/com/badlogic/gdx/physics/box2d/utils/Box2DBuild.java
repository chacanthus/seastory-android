// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.physics.box2d.utils;

import com.badlogic.gdx.jnigen.AntScriptGenerator;
import com.badlogic.gdx.jnigen.BuildConfig;
import com.badlogic.gdx.jnigen.BuildTarget;
import com.badlogic.gdx.jnigen.BuildTarget$TargetOs;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;
import java.io.File;

public class Box2DBuild {
    public Box2DBuild() {
        super();
    }

    public static void main(String[] args) throws Exception {
        BuildTarget v6 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Windows, false);
        BuildTarget v7 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Windows, true);
        BuildTarget v2 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Linux, false);
        BuildTarget v3 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Linux, true);
        BuildTarget v0 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Android, false);
        BuildTarget v4 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.MacOsX, false);
        BuildTarget v5 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.MacOsX, true);
        BuildTarget v1 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.IOS, false);
        new NativeCodeGenerator().generate("src", "bin" + File.pathSeparator + "../../../gdx/bin", "jni");
        AntScriptGenerator v8 = new AntScriptGenerator();
        BuildConfig v9 = new BuildConfig("gdx-box2d");
        BuildTarget[] v10 = new BuildTarget[8];
        v10[0] = v6;
        v10[1] = v7;
        v10[2] = v2;
        v10[3] = v3;
        v10[4] = v4;
        v10[5] = v5;
        v10[6] = v0;
        v10[7] = v1;
        v8.generate(v9, v10);
    }
}

