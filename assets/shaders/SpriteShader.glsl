
#version 330 core
layout(location=0)in vec3 aPos;
layout(location=1)in vec4 aColor;
layout(location=2)in vec2 aTexCoords;
layout(location=3)in float aTexId;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out vec2 fTexCoords;
out float fTexId;

void main()
{
    fColor=aColor;
    fTexCoords=aTexCoords;
    fTexId=aTexId;
    
    gl_Position=uProjection*uView*vec4(aPos,1.0);
}

//Type
#version 330 core

in vec4 fColor;
in vec2 fTexCoords;
in float fTexId;

uniform sampler2D uTextures[8];

void main()
{
    if(fTexId>=0){
        
        int id=int(fTexId);
        
        switch(id){
            case 0:
            gl_FragColor=fColor*texture(uTextures[0],fTexCoords);
            break;
            case 1:
            gl_FragColor=fColor*texture(uTextures[1],fTexCoords);
            break;
            case 2:
            gl_FragColor=fColor*texture(uTextures[2],fTexCoords);
            break;
            case 3:
            gl_FragColor=fColor*texture(uTextures[3],fTexCoords);
            break;
            case 4:
            gl_FragColor=fColor*texture(uTextures[4],fTexCoords);
            break;
            case 5:
            gl_FragColor=fColor*texture(uTextures[5],fTexCoords);
            break;
            case 6:
            gl_FragColor=fColor*texture(uTextures[6],fTexCoords);
            break;
            case 7:
            gl_FragColor=fColor*texture(uTextures[7],fTexCoords);
            break;
        }
    }else{
        gl_FragColor=vec4(1,0,0,1);
    }
}

