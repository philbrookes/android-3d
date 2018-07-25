precision mediump float;       // Set the default precision to medium. We don't need as high of a
                               // precision in the fragment shader.
varying vec4 v_Color;          // This is the color from the vertex shader interpolated across the
                               // triangle per fragment.
void main()                    // The entry point for our fragment shader.
{
   gl_FragColor = vec4(0.0f, 0.0f, 0.0f, 1.0f);     // Pass the color directly through the pipeline.
}