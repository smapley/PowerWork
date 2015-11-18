package cn.pedant.SweetAlert;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SweetAlertDialog extends Dialog implements View.OnClickListener {
    private final int DISMISS = 1;
    private View mDialogView;
    private AnimationSet mModalInAnim;
    private AnimationSet mModalOutAnim;
    private Animation mOverlayOutAnim;
    private Animation mErrorInAnim;
    private AnimationSet mErrorXInAnim;
    private AnimationSet mSuccessLayoutAnimSet;
    private Animation mSuccessBowAnim;
    private Animation mProgressBowAnim;
    private TextView mTitleTextView;
    private TextView mContentTextView;
    private String mTitleText;
    private String mContentText;
    private boolean mShowCancel;
    private boolean mShowConfirm;
    private boolean mShowCenter;
    private boolean mshowEditext;
    private String mCancelText;
    private String mCenterText;
    private String mConfirmText;
    private int mAlertType;
    private FrameLayout mErrorFrame;
    private FrameLayout mSuccessFrame;
    private FrameLayout mProgressFrame;
    private SuccessTickView mSuccessTick;
    private ImageView mErrorX;
    private View mSuccessLeftMask;
    private View mSuccessRightMask;
    private View mProgressRightMask;
    private Drawable mCustomImgDrawable;
    private ImageView mCustomImage;
    private Button mConfirmButton;
    private Button mCancelButton;
    private Button mCenterButton;
    private FrameLayout mWarningFrame;
    private OnSweetClickListener onSweetClickListener;
    private EditText meditext;
    private Context context;

    public static final int NORMAL_TYPE = 0;
    public static final int ERROR_TYPE = 1;
    public static final int SUCCESS_TYPE = 2;
    public static final int WARNING_TYPE = 3;
    public static final int CUSTOM_IMAGE_TYPE = 4;
    public static final int EDITEXT_TYPE = 5;
    public static final int PROGRESS_TYPE = 6;

    public static interface OnSweetClickListener {
        public void onClick(SweetAlertDialog sweetAlertDialog, int item);
    }

    public SweetAlertDialog(Context context) {
        this(context, NORMAL_TYPE);
        this.context = context;
    }

    public SweetAlertDialog(Context context, String title, String content, int[] button, String[] btName, OnSweetClickListener onSweetClickListener) {
        this(context, NORMAL_TYPE, title, content, button, btName, onSweetClickListener);
    }

    public SweetAlertDialog(Context context, int alertType, int title, int[] button, int[] btName, OnSweetClickListener onSweetClickListener) {
        this(context, alertType, title, 0, button, btName, onSweetClickListener);
    }

    public SweetAlertDialog(Context context, int alertType, int title, int content, int[] button, int[] btName, OnSweetClickListener onSweetClickListener) {
        this(context, alertType);
        if (title != 0) {
            setTitleText(context.getString(title));
        }
        if (content != 0) {
            setContentText(context.getString(content));
        }
        if (button != null) {
            for (int i = 0; i < button.length; i++) {
                if (button[i] == 1) {
                    showButton(i, context.getString(btName[i]));
                }
            }
        }
        if (onSweetClickListener != null) {
            setOnSweetClickListener(onSweetClickListener);
        }

    }

    /**
     * @param context   上下文
     * @param alertType 类型
     * @param title     标题
     * @param content   内容
     * @param button    是否显示按钮 1显示 0不显示
     * @param btName    按钮名字
     */
    public SweetAlertDialog(Context context, int alertType, String title, String content, int[] button, String[] btName, OnSweetClickListener onSweetClickListener) {
        this(context, alertType);
        if (title != null) {
            setTitleText(title);
        }
        if (content != null) {
            setContentText(content);
        }
        if (button != null) {
            for (int i = 0; i < button.length; i++) {
                if (button[i] == 1) {
                    showButton(i, btName[i]);
                }
            }
        }
        if (onSweetClickListener != null) {
            setOnSweetClickListener(onSweetClickListener);
        }
    }

    private void showButton(int i, String btName) {
        switch (i) {
            case 0:
                showCancelButton(true);
                setCancelText(btName);
                break;
            case 1:
                showCenterButton(true);
                setCenterText(btName);
                break;
            case 2:
                showConfirmButton(true);
                setConfirmText(btName);
                break;
        }

    }


    public SweetAlertDialog(Context context, int alertType) {
        super(context, R.style.alert_dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        mAlertType = alertType;
        mErrorInAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.error_frame_in);
        mErrorXInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.error_x_in);
        // 2.3.x system don't support alpha-animation on layer-list drawable
        // remove it from animation set
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            List<Animation> childAnims = mErrorXInAnim.getAnimations();
            int idx = 0;
            for (; idx < childAnims.size(); idx++) {
                if (childAnims.get(idx) instanceof AlphaAnimation) {
                    break;
                }
            }
            if (idx < childAnims.size()) {
                childAnims.remove(idx);
            }
        }
        mSuccessBowAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.success_bow_roate);
        mProgressBowAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.progress_bow_roate);
        mSuccessLayoutAnimSet = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.success_mask_layout);
        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_in);
        mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        SweetAlertDialog.super.dismiss();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // dialog overlay fade out
        mOverlayOutAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                WindowManager.LayoutParams wlp = getWindow().getAttributes();
                wlp.alpha = 1 - interpolatedTime;
                getWindow().setAttributes(wlp);
            }
        };
        mOverlayOutAnim.setDuration(120);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mTitleTextView = (TextView) findViewById(R.id.title_text);
        mContentTextView = (TextView) findViewById(R.id.content_text);
        mErrorFrame = (FrameLayout) findViewById(R.id.error_frame);
        mErrorX = (ImageView) mErrorFrame.findViewById(R.id.error_x);
        mSuccessFrame = (FrameLayout) findViewById(R.id.success_frame);
        mSuccessTick = (SuccessTickView) mSuccessFrame.findViewById(R.id.success_tick);
        mSuccessLeftMask = mSuccessFrame.findViewById(R.id.mask_left);
        mSuccessRightMask = mSuccessFrame.findViewById(R.id.mask_right);
        mProgressFrame = (FrameLayout) findViewById(R.id.progress_frame);
        mProgressRightMask = mProgressFrame.findViewById(R.id.progress_right);
        mCustomImage = (ImageView) findViewById(R.id.custom_image);
        mWarningFrame = (FrameLayout) findViewById(R.id.warning_frame);
        mConfirmButton = (Button) findViewById(R.id.confirm_button);
        mCancelButton = (Button) findViewById(R.id.cancel_button);
        mCenterButton = (Button) findViewById(R.id.center_button);
        meditext = (EditText) findViewById(R.id.content_editext);

        mConfirmButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
        mCenterButton.setOnClickListener(this);

        setTitleText(mTitleText);
        setContentText(mContentText);
        showCancelButton(mShowCancel);
        showCenterButton(mShowCenter);
        showConfirmButton(mShowConfirm);
        showEditext(mshowEditext);
        setCancelText(mCancelText);
        setCenterText(mCenterText);
        setConfirmText(mConfirmText);
        changeAlertType(mAlertType, true);
    }

    private void restore() {
        mCustomImage.setVisibility(View.GONE);
        mErrorFrame.setVisibility(View.GONE);
        mSuccessFrame.setVisibility(View.GONE);
        mProgressFrame.setVisibility(View.GONE);
        mWarningFrame.setVisibility(View.GONE);

        mErrorFrame.clearAnimation();
        mErrorX.clearAnimation();
        mSuccessTick.clearAnimation();
        mSuccessLeftMask.clearAnimation();
        mSuccessRightMask.clearAnimation();
        mProgressRightMask.clearAnimation();
    }

    private void playAnimation() {
        if (mAlertType == ERROR_TYPE) {
            mErrorFrame.startAnimation(mErrorInAnim);
            mErrorX.startAnimation(mErrorXInAnim);
        } else if (mAlertType == SUCCESS_TYPE) {
            mSuccessTick.startTickAnim();
            mSuccessRightMask.startAnimation(mSuccessBowAnim);
        } else if (mAlertType == PROGRESS_TYPE) {
            mProgressRightMask.startAnimation(mProgressBowAnim);
            mProgressBowAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mProgressRightMask.startAnimation(mProgressBowAnim);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    private void changeAlertType(int alertType, boolean fromCreate) {
        mAlertType = alertType;
        // call after created views
        if (mDialogView != null) {
            if (!fromCreate) {
                // restore all of views state before switching alert type
                restore();
            }
            switch (mAlertType) {
                case ERROR_TYPE:
                    mErrorFrame.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS_TYPE:
                    mSuccessFrame.setVisibility(View.VISIBLE);
                    // initial rotate layout of success mask
                    mSuccessLeftMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(0));
                    mSuccessRightMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(1));
                    break;
                case PROGRESS_TYPE:
                    mProgressFrame.setVisibility(View.VISIBLE);
                    // initial rotate layout of success mask
                    break;
                case WARNING_TYPE:
                    mConfirmButton.setBackgroundResource(R.drawable.red_button_background);
                    mWarningFrame.setVisibility(View.VISIBLE);
                    break;
                case CUSTOM_IMAGE_TYPE:
                    setCustomImage(mCustomImgDrawable);
                    break;
            }
            if (!fromCreate) {
                playAnimation();
            }
        }
    }

    public int getAlerType() {
        return mAlertType;
    }

    public void changeAlertType(int alertType) {
        changeAlertType(alertType, false);
    }


    public String getTitleText() {
        return mTitleText;
    }

    public SweetAlertDialog setTitleText(String text) {
        mTitleText = text;
        if (mTitleTextView != null && mTitleText != null) {
            mTitleTextView.setText(mTitleText);
        }
        return this;
    }

    public SweetAlertDialog setCustomImage(Drawable drawable) {
        mCustomImgDrawable = drawable;
        if (mCustomImage != null && mCustomImgDrawable != null) {
            mCustomImage.setVisibility(View.VISIBLE);
            mCustomImage.setImageDrawable(mCustomImgDrawable);
        }
        return this;
    }

    public SweetAlertDialog setCustomImage(int resourceId) {
        return setCustomImage(getContext().getResources().getDrawable(resourceId));
    }

    public String getContentText() {
        return mContentText;
    }

    public SweetAlertDialog setContentText(String text) {
        mContentText = text;
        if (mContentTextView != null && mContentText != null) {
            mContentTextView.setVisibility(View.VISIBLE);
            mContentTextView.setText(mContentText);
        }
        return this;
    }

    public SweetAlertDialog setContentText(int id) {
        return setContentText(context.getString(id));
    }

    public SweetAlertDialog showEditext(boolean isShow) {
        mshowEditext = isShow;
        if (meditext != null) {
            meditext.setVisibility(mshowEditext ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public String getEditext() {
        if (meditext != null) {
            return meditext.getText().toString();
        }
        return "";
    }

    public boolean isShowCancelButton() {
        return mShowCancel;
    }

    public boolean isShowConfirmButton() {
        return mShowConfirm;
    }

    public boolean isShowCenterButton() {
        return mShowCenter;
    }

    public SweetAlertDialog showCancelButton(boolean isShow) {
        mShowCancel = isShow;
        if (mCancelButton != null) {
            mCancelButton.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public SweetAlertDialog showConfirmButton(boolean isShow) {
        mShowConfirm = isShow;
        if (mConfirmButton != null) {
            mConfirmButton.setVisibility(mShowConfirm ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public SweetAlertDialog showCenterButton(boolean isShow) {
        mShowCenter = isShow;
        if (mCenterButton != null) {
            mCenterButton.setVisibility(mShowCenter ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public String getCancelText() {
        return mCancelText;
    }

    public String getCenterText() {
        return mCenterText;
    }

    public SweetAlertDialog setCancelText(String text) {
        mCancelText = text;
        if (mCancelButton != null && mCancelText != null) {
            mCancelButton.setText(mCancelText);
        }
        return this;
    }

    public SweetAlertDialog setCenterText(String text) {
        mCenterText = text;
        if (mCenterButton != null && mCenterText != null) {
            mCenterButton.setText(mCenterText);
        }
        return this;
    }

    public String getConfirmText() {
        return mConfirmText;
    }

    public SweetAlertDialog setConfirmText(String text) {
        mConfirmText = text;
        if (mConfirmButton != null && mConfirmText != null) {
            mConfirmButton.setText(mConfirmText);
        }
        return this;
    }


    public SweetAlertDialog setOnSweetClickListener(OnSweetClickListener listener) {
        onSweetClickListener = listener;
        return this;
    }


    protected void onStart() {
        mDialogView.startAnimation(mModalInAnim);
        playAnimation();
    }

    public void dismiss(final long time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mhandler.obtainMessage(DISMISS).sendToTarget();
            }
        }).start();
    }

    public void dismiss() {
        mConfirmButton.startAnimation(mOverlayOutAnim);
        mDialogView.startAnimation(mModalOutAnim);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_button) {
            if (onSweetClickListener != null) {
                onSweetClickListener.onClick(SweetAlertDialog.this, 1);
            } else {
                dismiss();
            }
        } else if (v.getId() == R.id.center_button) {
            if (onSweetClickListener != null) {
                onSweetClickListener.onClick(SweetAlertDialog.this, 2);
            } else {
                dismiss();
            }
        } else if (v.getId() == R.id.confirm_button) {
            if (onSweetClickListener != null) {
                onSweetClickListener.onClick(SweetAlertDialog.this, 3);
            } else {
                dismiss();
            }
        }
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DISMISS:
                    dismiss();
                    break;
            }
        }
    };
}
