
uniform float playerX;
uniform float playerY;

//this position
varying vec4 pos;


void main(void)
{

	vec2 a = vec2(pos.xy);
	vec2 b = vec2(playerX, playerY);
	
	float d = distance(a, b);
	
	if(gl_FragCoord.x < 100)
	{
		gl_FragColor = vec4(0.0, 1.0, 0.0 , 1.0);
	}
	else
	{
		gl_FragColor = vec4(1.0, 0.0, 0.0 , 1.0);
	}
	
	/*
	vec2 pos = mod(gl_FragCoord.xy, vec2(50.0)) - vec2(25.0);
      float dist_squared = dot(pos, pos);
  
      gl_FragColor = (dist_squared < 400.0) 
          ? vec4(.90, .90, .90, 1.0)
          : vec4(.20, .20, .40, 1.0);
	*/
	//if(d > 100 )
	//	gl_FragColor = vec4(0.0, 0.0, 0.0 , 0.5);
	//else
		
}