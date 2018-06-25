package com.jinoux.pwersupplysocket.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.jinoux.powersupplysocket.C0048R;

public class CustomDialog extends Dialog {

    public static class Builder {
        private static int theme;
        private View contentView;
        private Context context;
        private String message;
        private OnClickListener negativeButtonClickListener;
        private String negativeButtonText;
        private OnClickListener positiveButtonClickListener;
        private String positiveButtonText;
        private String title;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder(Context context, int theme) {
            this.context = context;
            theme = theme;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = (String) this.context.getText(message);
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String) this.context.getText(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) this.context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) this.context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public CustomDialog create() {
            View layout;
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
            final CustomDialog dialog = new CustomDialog(this.context, C0048R.style.Dialog);
            if (theme == 0) {
                layout = inflater.inflate(C0048R.layout.dialog_normal_layout, null);
            } else if (theme == 1) {
                layout = inflater.inflate(C0048R.layout.dialog_picture_layout, null);
            } else {
                layout = inflater.inflate(C0048R.layout.dialog_normal_layout, null);
            }
            dialog.addContentView(layout, new LayoutParams(-1, -2));
            if (theme != 0) {
            }
            if (this.positiveButtonText != null) {
                ((Button) layout.findViewById(C0048R.id.positiveButton)).setText(this.positiveButtonText);
                if (this.positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(C0048R.id.positiveButton)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Builder.this.positiveButtonClickListener.onClick(dialog, -1);
                        }
                    });
                }
            } else {
                layout.findViewById(C0048R.id.positiveButton).setVisibility(8);
            }
            if (this.negativeButtonText != null) {
                ((Button) layout.findViewById(C0048R.id.negativeButton)).setText(this.negativeButtonText);
                if (this.negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(C0048R.id.negativeButton)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Builder.this.negativeButtonClickListener.onClick(dialog, -2);
                        }
                    });
                }
            } else {
                layout.findViewById(C0048R.id.negativeButton).setVisibility(8);
            }
            if (this.message != null) {
                ((TextView) layout.findViewById(C0048R.id.message)).setText(this.message);
            } else if (this.contentView != null) {
                ((LinearLayout) layout.findViewById(C0048R.id.message)).removeAllViews();
                ((LinearLayout) layout.findViewById(C0048R.id.message)).addView(this.contentView, new LayoutParams(-2, -2));
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }
}
