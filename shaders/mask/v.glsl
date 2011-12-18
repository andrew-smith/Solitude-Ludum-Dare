

uniform float playerX;
uniform float playerY;

//pass along to frag shader
varying vec4 pos;


const vec4 C_BLACK = vec4(0.0,0.0,0.0,1.0);
const vec4 C_TRANS = vec4(0.0,0.0,0.0,0.0);

void main(void)
{
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	vec2 player = vec2(playerX, playerY);
	pos = mix(C_BLACK, C_TRANS, distance(player,gl_Position)/100);
	
	
	
}