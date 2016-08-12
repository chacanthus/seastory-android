// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;
import com.badlogic.gdx.utils.Array;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

class MtlLoader {
    public Array materials;

    MtlLoader() {
        super();
        this.materials = new Array();
    }

    public ModelMaterial getMaterial(String name) {
        Iterator v0 = this.materials.iterator();
        do {
            if(v0.hasNext()) {
                Object v1 = v0.next();
                if(!((ModelMaterial)v1).id.equals(name)) {
                    continue;
                }
            }
            else {
                break;
            }

            goto label_8;
        }
        while(true);

        ModelMaterial v2 = new ModelMaterial();
        v2.id = name;
        v2.diffuse = new Color(Color.WHITE);
        this.materials.add(v2);
        ModelMaterial v1_1 = v2;
    label_8:
        return v1_1;
    }

    public void load(FileHandle file) {  // has try-catch handlers
        float v2;
        float v3;
        float v8;
        float v13;
        ModelTexture v18;
        ModelMaterial v11;
        String[] v20;
        String v4 = "default";
        Color v5 = Color.WHITE;
        Color v16 = Color.WHITE;
        float v12 = 1f;
        float v15 = 0f;
        String v19 = null;
        if(file == null) {
            return;
        }

        if(file.exists()) {
            goto label_10;
        }

        return;
    label_10:
        BufferedReader v14 = new BufferedReader(new InputStreamReader(file.read()), 4096);
        Color v17 = v16;
        Color v6 = v5;
        try {
            while(true) {
            label_18:
                String v10 = v14.readLine();
                if(v10 == null) {
                    goto label_202;
                }

                if(v10.length() > 0 && v10.charAt(0) == 9) {
                    v10 = v10.substring(1).trim();
                }

                v20 = v10.split("\\s+");
                if(v20[0].length() == 0) {
                    continue;
                }

                if(v20[0].charAt(0) == 35) {
                    continue;
                }

                String v9 = v20[0].toLowerCase();
                if(v9.equals("newmtl")) {
                    v11 = new ModelMaterial();
                    v11.id = v4;
                    v11.diffuse = new Color(v6);
                    v11.specular = new Color(v17);
                    v11.opacity = v12;
                    v11.shininess = v15;
                    if(v19 != null) {
                        v18 = new ModelTexture();
                        v18.usage = null;
                        v18.fileName = new String(v19);
                        if(v11.textures == null) {
                            v11.textures = new Array(1);
                        }

                        v11.textures.add(v18);
                    }

                    this.materials.add(v11);
                    if(v20.length > 1) {
                        v4 = v20[1].replace(null, '_');
                    }
                    else {
                        v4 = "default";
                    }

                    v5 = Color.WHITE;
                    goto label_115;
                }
                else {
                    goto label_123;
                }
            }
        }
        catch(IOException v7) {
            goto label_254;
        }

        try {
        label_115:
            v16 = Color.WHITE;
            v12 = 1f;
            v15 = 0f;
            goto label_118;
        }
        catch(IOException v7) {
            goto label_257;
        }

        try {
        label_123:
            if(!v9.equals("kd") && !v9.equals("ks")) {
                if(!v9.equals("tr") && !v9.equals("d")) {
                    if(v9.equals("ns")) {
                        v15 = Float.parseFloat(v20[1]);
                        v16 = v17;
                        v5 = v6;
                        goto label_118;
                    }
                    else if(v9.equals("map_kd")) {
                        v19 = file.parent().child(v20[1]).path();
                        v16 = v17;
                        v5 = v6;
                        goto label_118;
                    }
                    else {
                        goto label_261;
                    }
                }

                goto label_174;
            }

            goto label_131;
        }
        catch(IOException v7) {
            goto label_254;
        }

    label_261:
        v16 = v17;
        v5 = v6;
        goto label_118;
        try {
        label_174:
            v12 = Float.parseFloat(v20[1]);
            v16 = v17;
            v5 = v6;
            goto label_118;
        label_131:
            v13 = Float.parseFloat(v20[1]);
            v8 = Float.parseFloat(v20[2]);
            v3 = Float.parseFloat(v20[3]);
            v2 = 1f;
            if(v20.length > 4) {
                v2 = Float.parseFloat(v20[4]);
            }

            if(!v20[0].toLowerCase().equals("kd")) {
                goto label_161;
            }

            v5 = new Color();
            goto label_158;
        }
        catch(IOException v7) {
            goto label_254;
        }

        try {
        label_158:
            v5.set(v13, v8, v3, v2);
            v16 = v17;
            goto label_118;
        }
        catch(IOException v7) {
        label_257:
            return;
        }

        try {
        label_161:
            v16 = new Color();
            goto label_162;
        }
        catch(IOException v7) {
            goto label_254;
        }

        try {
        label_162:
            v16.set(v13, v8, v3, v2);
            v5 = v6;
        }
        catch(IOException v7) {
            return;
        }

    label_118:
        v17 = v16;
        v6 = v5;
        goto label_18;
        try {
        label_202:
            v14.close();
        }
        catch(IOException v7) {
        label_254:
            return;
        }

        v11 = new ModelMaterial();
        v11.id = v4;
        v11.diffuse = new Color(v6);
        v11.specular = new Color(v17);
        v11.opacity = v12;
        v11.shininess = v15;
        if(v19 != null) {
            v18 = new ModelTexture();
            v18.usage = null;
            v18.fileName = new String(v19);
            if(v11.textures == null) {
                v11.textures = new Array(1);
            }

            v11.textures.add(v18);
        }

        this.materials.add(v11);
    }
}

