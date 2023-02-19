#type vertex

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
    
    gl_Position=uProjection*uView*vec4(aPos,1.);
}

#type fragment
#version 330 core

in vec4 fColor;
in vec2 fTexCoords;
in float fTexId;

uniform sampler2D uTextures[8];

void main()
{
    if(fTexId>=0){
        
        int id=int(fTexId);
        float c;
        
        switch(id){
            case 0:
            c=texture(uTextures[0],fTexCoords).b;
            gl_FragColor=vec4(c,c,c,1)*fColor;
            break;
            case 1:
            c=texture(uTextures[1],fTexCoords).b;
            gl_FragColor=vec4(c,c,c,1)*fColor;
            break;
            case 2:
            c=texture(uTextures[2],fTexCoords).b;
            gl_FragColor=vec4(c,c,c,1)*fColor;
            break;
            case 3:
            c=texture(uTextures[3],fTexCoords).b;
            gl_FragColor=vec4(c,c,c,1)*fColor;
            break;
            case 4:
            c=texture(uTextures[4],fTexCoords).b;
            gl_FragColor=vec4(c,c,c,1)*fColor;
            break;
            case 5:
            c=texture(uTextures[5],fTexCoords).b;
            gl_FragColor=vec4(c,c,c,1)*fColor;
            break;
            case 6:
            c=texture(uTextures[6],fTexCoords).b;
            gl_FragColor=vec4(c,c,c,1)*fColor;
            break;
            case 7:
            c=texture(uTextures[7],fTexCoords).b;
            gl_FragColor=vec4(c,c,c,1)*fColor;
            break;
        }
    }else{
        gl_FragColor=vec4(1,0,0,1);
    }
}

