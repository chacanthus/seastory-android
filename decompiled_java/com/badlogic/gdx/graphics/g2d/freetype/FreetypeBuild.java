// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g2d.freetype;

import com.badlogic.gdx.jnigen.AntScriptGenerator;
import com.badlogic.gdx.jnigen.BuildConfig;
import com.badlogic.gdx.jnigen.BuildTarget;
import com.badlogic.gdx.jnigen.BuildTarget$TargetOs;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;

public class FreetypeBuild {
    public FreetypeBuild() {
        super();
    }

    public static void main(String[] args) throws Exception {
        String[] v1 = new String[1];
        v1[0] = "freetype-2.5.5/include";
        String[] v7 = new String[44];
        v7[0] = "freetype-2.5.5/src/base/ftsystem.c";
        v7[1] = "freetype-2.5.5/src/base/ftinit.c";
        v7[2] = "freetype-2.5.5/src/base/ftdebug.c";
        v7[3] = "freetype-2.5.5/src/base/ftbase.c";
        v7[4] = "freetype-2.5.5/src/base/ftbbox.c";
        v7[5] = "freetype-2.5.5/src/base/ftglyph.c";
        v7[6] = "freetype-2.5.5/src/base/ftbdf.c";
        v7[7] = "freetype-2.5.5/src/base/ftbitmap.c";
        v7[8] = "freetype-2.5.5/src/base/ftcid.c";
        v7[9] = "freetype-2.5.5/src/base/ftfstype.c";
        v7[10] = "freetype-2.5.5/src/base/ftgasp.c";
        v7[11] = "freetype-2.5.5/src/base/ftgxval.c";
        v7[12] = "freetype-2.5.5/src/base/ftlcdfil.c";
        v7[13] = "freetype-2.5.5/src/base/ftmm.c";
        v7[14] = "freetype-2.5.5/src/base/ftotval.c";
        v7[15] = "freetype-2.5.5/src/base/ftpatent.c";
        v7[16] = "freetype-2.5.5/src/base/ftpfr.c";
        v7[17] = "freetype-2.5.5/src/base/ftstroke.c";
        v7[18] = "freetype-2.5.5/src/base/ftsynth.c";
        v7[19] = "freetype-2.5.5/src/base/fttype1.c";
        v7[20] = "freetype-2.5.5/src/base/ftwinfnt.c";
        v7[21] = "freetype-2.5.5/src/base/ftxf86.c";
        v7[22] = "freetype-2.5.5/src/bdf/bdf.c";
        v7[23] = "freetype-2.5.5/src/cff/cff.c";
        v7[24] = "freetype-2.5.5/src/cid/type1cid.c";
        v7[25] = "freetype-2.5.5/src/pcf/pcf.c";
        v7[26] = "freetype-2.5.5/src/pfr/pfr.c";
        v7[27] = "freetype-2.5.5/src/sfnt/sfnt.c";
        v7[28] = "freetype-2.5.5/src/truetype/truetype.c";
        v7[29] = "freetype-2.5.5/src/type1/type1.c";
        v7[30] = "freetype-2.5.5/src/type42/type42.c";
        v7[31] = "freetype-2.5.5/src/winfonts/winfnt.c";
        v7[32] = "freetype-2.5.5/src/raster/raster.c";
        v7[33] = "freetype-2.5.5/src/smooth/smooth.c";
        v7[34] = "freetype-2.5.5/src/autofit/autofit.c";
        v7[35] = "freetype-2.5.5/src/cache/ftcache.c";
        v7[36] = "freetype-2.5.5/src/gzip/ftgzip.c";
        v7[37] = "freetype-2.5.5/src/lzw/ftlzw.c";
        v7[38] = "freetype-2.5.5/src/bzip2/ftbzip2.c";
        v7[39] = "freetype-2.5.5/src/gxvalid/gxvalid.c";
        v7[40] = "freetype-2.5.5/src/otvalid/otvalid.c";
        v7[41] = "freetype-2.5.5/src/psaux/psaux.c";
        v7[42] = "freetype-2.5.5/src/pshinter/pshinter.c";
        v7[43] = "freetype-2.5.5/src/psnames/psnames.c";
        BuildTarget v9 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Windows, false);
        v9.compilerPrefix = "";
        v9.buildFileName = "build-windows32home.xml";
        v9.excludeFromMasterBuildFile = true;
        v9.headerDirs = v1;
        v9.cIncludes = v7;
        v9.cFlags = v9.cFlags + " -std=c99 -DFT2_BUILD_LIBRARY";
        v9.cppFlags = v9.cppFlags + " -std=c99 -DFT2_BUILD_LIBRARY";
        BuildTarget v8 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Windows, false);
        v8.headerDirs = v1;
        v8.cIncludes = v7;
        v8.cFlags = v8.cFlags + " -std=c99 -DFT2_BUILD_LIBRARY";
        v8.cppFlags = v8.cppFlags + " -std=c99 -DFT2_BUILD_LIBRARY";
        BuildTarget v10 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Windows, true);
        v10.headerDirs = v1;
        v10.cIncludes = v7;
        v10.cFlags = v10.cFlags + " -std=c99 -DFT2_BUILD_LIBRARY";
        v10.cppFlags = v10.cppFlags + " -std=c99 -DFT2_BUILD_LIBRARY";
        BuildTarget v3 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Linux, false);
        v3.headerDirs = v1;
        v3.cIncludes = v7;
        v3.cFlags = v3.cFlags + " -std=c99 -DFT2_BUILD_LIBRARY";
        v3.cppFlags = v3.cppFlags + " -std=c99 -DFT2_BUILD_LIBRARY";
        BuildTarget v4 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Linux, true);
        v4.headerDirs = v1;
        v4.cIncludes = v7;
        v4.cFlags = v4.cFlags + " -std=c99 -DFT2_BUILD_LIBRARY";
        v4.cppFlags = v4.cppFlags + " -std=c99 -DFT2_BUILD_LIBRARY";
        BuildTarget v5 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.MacOsX, false);
        v5.headerDirs = v1;
        v5.cIncludes = v7;
        v5.cFlags = v5.cFlags + " -DFT2_BUILD_LIBRARY";
        v5.cppFlags = v5.cppFlags + " -DFT2_BUILD_LIBRARY";
        v5.linkerFlags = v5.linkerFlags + " -framework CoreServices -framework Carbon";
        BuildTarget v6 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.MacOsX, true);
        v6.headerDirs = v1;
        v6.cIncludes = v7;
        v6.cFlags = v6.cFlags + " -DFT2_BUILD_LIBRARY";
        v6.cppFlags = v6.cppFlags + " -DFT2_BUILD_LIBRARY";
        v6.linkerFlags = v6.linkerFlags + " -framework CoreServices -framework Carbon";
        BuildTarget v0 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.Android, false);
        v0.headerDirs = v1;
        v0.cIncludes = v7;
        v0.cFlags = v0.cFlags + " -std=c99 -DFT2_BUILD_LIBRARY";
        v0.cppFlags = v0.cppFlags + " -std=c99 -DFT2_BUILD_LIBRARY";
        BuildTarget v2 = BuildTarget.newDefaultTarget(BuildTarget$TargetOs.IOS, false);
        v2.headerDirs = v1;
        v2.cIncludes = v7;
        v2.cFlags = v2.cFlags + " -DFT2_BUILD_LIBRARY";
        v2.cppFlags = v2.cppFlags + " -DFT2_BUILD_LIBRARY";
        new NativeCodeGenerator().generate();
        AntScriptGenerator v11 = new AntScriptGenerator();
        BuildConfig v12 = new BuildConfig("gdx-freetype");
        BuildTarget[] v13 = new BuildTarget[9];
        v13[0] = v9;
        v13[1] = v8;
        v13[2] = v10;
        v13[3] = v3;
        v13[4] = v4;
        v13[5] = v5;
        v13[6] = v6;
        v13[7] = v0;
        v13[8] = v2;
        v11.generate(v12, v13);
    }
}

