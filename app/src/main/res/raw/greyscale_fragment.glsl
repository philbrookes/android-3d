precision mediump float;       // Set the default precision to medium. We don't need as high of a
                               // precision in the fragment shader.

varying vec4 v_Color;          // This is the color from the vertex shader interpolated across the
                               // triangle per fragment.

const float cf = 0.0;          // 1.0 = greyscale, 0.0 = original colours

void main()                    // The entry point for our fragment shader.
{
    float grey = 0.21 * v_Color.r + 0.71 * v_Color.g + 0.07 * v_Color.b;
    gl_FragColor = vec4(v_Color.r * cf + grey * (1.0 - cf), v_Color.g * cf + grey * (1.0 - cf), v_Color.b * cf + grey * (1.0 - cf), 1.0);
}