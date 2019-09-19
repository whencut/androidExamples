package com.mamlambo.fallingsnow;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.renderscript.RSDriverException;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;
import android.view.Surface;
import android.view.SurfaceHolder;

public class RenderScriptGL extends RenderScript {
    int mWidth;
    int mHeight;

    public static class SurfaceConfig {
        int mDepthMin       = 0;
        int mDepthPref      = 0;
        int mStencilMin     = 0;
        int mStencilPref    = 0;
        int mColorMin       = 8;
        int mColorPref      = 8;
        int mAlphaMin       = 0;
        int mAlphaPref      = 0;
        int mSamplesMin     = 1;
        int mSamplesPref    = 1;
        float mSamplesQ     = 1.f;
        /**
         * @deprecated in API 16
         */
        public SurfaceConfig() {
        }
        /**
         * @deprecated in API 16
         */
        public SurfaceConfig(SurfaceConfig sc) {
            mDepthMin = sc.mDepthMin;
            mDepthPref = sc.mDepthPref;
            mStencilMin = sc.mStencilMin;
            mStencilPref = sc.mStencilPref;
            mColorMin = sc.mColorMin;
            mColorPref = sc.mColorPref;
            mAlphaMin = sc.mAlphaMin;
            mAlphaPref = sc.mAlphaPref;
            mSamplesMin = sc.mSamplesMin;
            mSamplesPref = sc.mSamplesPref;
            mSamplesQ = sc.mSamplesQ;
        }
        private void validateRange(int umin, int upref, int rmin, int rmax) {
            if (umin < rmin || umin > rmax) {
                throw new RSIllegalArgumentException("Minimum value provided out of range.");
            }
            if (upref < umin) {
                throw new RSIllegalArgumentException("preferred must be >= Minimum.");
            }
        }
        /**
         * @deprecated in API 16
         * Set the per-component bit depth for color (red, green, blue).  This
         * configures the surface for an unsigned integer buffer type.
         *
         * @param minimum
         * @param preferred
         */
        public void setColor(int minimum, int preferred) {
            validateRange(minimum, preferred, 5, 8);
            mColorMin = minimum;
            mColorPref = preferred;
        }
        /**
         * @deprecated in API 16
         * Set the bit depth for alpha. This configures the surface for
         * an unsigned integer buffer type.
         *
         * @param minimum
         * @param preferred
         */
        public void setAlpha(int minimum, int preferred) {
            validateRange(minimum, preferred, 0, 8);
            mAlphaMin = minimum;
            mAlphaPref = preferred;
        }
        /**
         * @deprecated in API 16
         * Set the bit depth for the depth buffer. This configures the
         * surface for an unsigned integer buffer type.  If a minimum of 0
         * is specified then its possible no depth buffer will be
         * allocated.
         *
         * @param minimum
         * @param preferred
         */
        public void setDepth(int minimum, int preferred) {
            validateRange(minimum, preferred, 0, 24);
            mDepthMin = minimum;
            mDepthPref = preferred;
        }
        /**
         * @deprecated in API 16
         * Configure the multisample rendering.
         *
         * @param minimum The required number of samples, must be at least 1.
         * @param preferred The targe number of samples, must be at least
         *                  minimum
         * @param Q  The quality of samples, range 0-1.  Used to decide between
         *           different formats which have the same number of samples but
         *           different rendering quality.
         */
        public void setSamples(int minimum, int preferred, float Q) {
            validateRange(minimum, preferred, 1, 32);
            if (Q < 0.0f || Q > 1.0f) {
                throw new RSIllegalArgumentException("Quality out of 0-1 range.");
            }
            mSamplesMin = minimum;
            mSamplesPref = preferred;
            mSamplesQ = Q;
        }
    };
    SurfaceConfig mSurfaceConfig;
    /**
     * @deprecated in API 16
     * Construct a new RenderScriptGL context.
     *
     * @param ctx The context.
     * @param sc The desired format of the primary rendering surface.
     */
    public RenderScriptGL(Context ctx, SurfaceConfig sc) {
        super(ctx);
        mSurfaceConfig = new SurfaceConfig(sc);
        int sdkVersion = ctx.getApplicationInfo().targetSdkVersion;
        mWidth = 0;
        mHeight = 0;
        mDev = nDeviceCreate();
        int dpi = ctx.getResources().getDisplayMetrics().densityDpi;
        mContext = nContextCreateGL(mDev, 0, sdkVersion,
                mSurfaceConfig.mColorMin, mSurfaceConfig.mColorPref,
                mSurfaceConfig.mAlphaMin, mSurfaceConfig.mAlphaPref,
                mSurfaceConfig.mDepthMin, mSurfaceConfig.mDepthPref,
                mSurfaceConfig.mStencilMin, mSurfaceConfig.mStencilPref,
                mSurfaceConfig.mSamplesMin, mSurfaceConfig.mSamplesPref,
                mSurfaceConfig.mSamplesQ, dpi);
        if (mContext == 0) {
            throw new RSDriverException("Failed to create RS context.");
        }
        mMessageThread = new MessageThread(this);
        mMessageThread.start();
    }
    /**
     * @deprecated in API 16
     * Bind an os surface
     *
     *
     * @param w
     * @param h
     * @param sur
     */
    public void setSurface(SurfaceHolder sur, int w, int h) {
        validate();
        Surface s = null;
        if (sur != null) {
            s = sur.getSurface();
        }
        mWidth = w;
        mHeight = h;
        nContextSetSurface(w, h, s);
    }
    /**
     * @deprecated in API 16
     * Bind an os surface
     *
     * @param w
     * @param h
     * @param sur
     */
    public void setSurfaceTexture(SurfaceTexture sur, int w, int h) {
        validate();
        //android.util.Log.v("rs", "set surface " + sur + " w=" + w + ", h=" + h);
        mWidth = w;
        mHeight = h;
        nContextSetSurfaceTexture(w, h, sur);
    }
    /**
     * @deprecated in API 16
     * return the height of the last set surface.
     *
     * @return int
     */
    public int getHeight() {
        return mHeight;
    }
    /**
     * @deprecated in API 16
     * return the width of the last set surface.
     *
     * @return int
     */
    public int getWidth() {
        return mWidth;
    }
    /**
     * @deprecated in API 16
     * Temporarly halt calls to the root rendering script.
     *
     */
    public void pause() {
        validate();
        nContextPause();
    }
    /**
     * @deprecated in API 16
     * Resume calls to the root rendering script.
     *
     */
    public void resume() {
        validate();
        nContextResume();
    }
    /**
     * @deprecated in API 16
     * Set the script to handle calls to render the primary surface.
     *
     * @param s Graphics script to process rendering requests.
     */
    public void bindRootScript(Script s) {
        validate();
        nContextBindRootScript(safeID(s));
    }
    /**
     * @deprecated in API 16
     * Set the default ProgramStore object seen as the parent state by the root
     * rendering script.
     *
     * @param p
     */
    public void bindProgramStore(ProgramStore p) {
        validate();
        nContextBindProgramStore(safeID(p));
    }
    /**
     * @deprecated in API 16
     * Set the default ProgramFragment object seen as the parent state by the
     * root rendering script.
     *
     * @param p
     */
    public void bindProgramFragment(ProgramFragment p) {
        validate();
        nContextBindProgramFragment(safeID(p));
    }
    /**
     * @deprecated in API 16
     * Set the default ProgramRaster object seen as the parent state by the
     * root rendering script.
     *
     * @param p
     */
    public void bindProgramRaster(ProgramRaster p) {
        validate();
        nContextBindProgramRaster(safeID(p));
    }
    /**
     * @deprecated in API 16
     * Set the default ProgramVertex object seen as the parent state by the
     * root rendering script.
     *
     * @param p
     */
    public void bindProgramVertex(ProgramVertex p) {
        validate();
        nContextBindProgramVertex(safeID(p));
    }
}
