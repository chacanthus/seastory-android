// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils;

import com.badlogic.gdx.jnigen.AntScriptGenerator;
import com.badlogic.gdx.jnigen.BuildConfig;
import com.badlogic.gdx.jnigen.BuildTarget;
import com.badlogic.gdx.jnigen.BuildTarget$TargetOs;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;

public class GdxBuild {
    public GdxBuild() {
        super();
    }

    public static void main(String[] args) throws Exception {
        NativeCodeGenerator v1 = new NativeCodeGenerator();
        String[] v5 = new String[1];
        v5[0] = "**/*";
        v1.generate("src", "bin", "jni", v5, null);
        String[] v9 = new String[2];
        v9[0] = "android/**";
        v9[1] = "iosgl/**";
        BuildTarget v16 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Windows, false);
        v16.compilerPrefix = "";
        v16.buildFileName = "build-windows32home.xml";
        v16.excludeFromMasterBuildFile = true;
        v16.cppExcludes = v9;
        BuildTarget v15 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Windows, false);
        v15.cppExcludes = v9;
        BuildTarget v17 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Windows, true);
        v17.cppExcludes = v9;
        BuildTarget v11 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Linux, false);
        v11.cppExcludes = v9;
        BuildTarget v12 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Linux, true);
        v12.cppExcludes = v9;
        BuildTarget v8 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Android, false);
        v8.linkerFlags = v8.linkerFlags + " -lGLESv2 -llog";
        String[] v1_1 = new String[1];
        v1_1[0] = "iosgl/**";
        v8.cppExcludes = v1_1;
        BuildTarget v13 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.MacOsX, false);
        v13.cppExcludes = v9;
        BuildTarget v14 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.MacOsX, true);
        v14.cppExcludes = v9;
        BuildTarget v10 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.IOS, false);
        v1_1 = new String[1];
        v1_1[0] = "android/**";
        v10.cppExcludes = v1_1;
        v1_1 = new String[1];
        v1_1[0] = "iosgl";
        v10.headerDirs = v1_1;
        AntScriptGenerator v1_2 = new AntScriptGenerator();
        BuildConfig v2 = new BuildConfig("gdx", "../target/native", "libs", "jni");
        BuildTarget[] v3 = new BuildTarget[9];
        v3[0] = v13;
        v3[1] = v14;
        v3[2] = v16;
        v3[3] = v15;
        v3[4] = v17;
        v3[5] = v11;
        v3[6] = v12;
        v3[7] = v8;
        v3[8] = v10;
        v1_2.generate(v2, v3);
    }
}

