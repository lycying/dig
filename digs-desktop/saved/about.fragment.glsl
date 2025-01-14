#ifdef GL_ES
precision highp float;
#endif
 
uniform vec2 resolution;
uniform float time;
 
void main(void)
{
  highp vec2 p = -1.0 + 2.0 * gl_FragCoord.xy / resolution.xy;
 
  highp float a = atan(p.y, p.x);
  highp float r = length(p) + 0.0001;
 
  highp float b = 1.9 * sin(8.0 * r - time - 2.0 * a);
  b = 0.3125 / r + cos(7.0 * a + b * b) / (100.0 * r);
  b *= smoothstep(0.2, 0.8, b);
 
  gl_FragColor = vec4(b, 0.67 * b + 0.1 * sin(a + time), 0.0, 1.0);
}