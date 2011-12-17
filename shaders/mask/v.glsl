
//pass along to frag shader
varying vec4 pos;


void main(void)
{
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	pos = gl_Position;
}