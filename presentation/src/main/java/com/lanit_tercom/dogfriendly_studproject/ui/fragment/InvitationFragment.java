package com.lanit_tercom.dogfriendly_studproject.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lanit_tercom.dogfriendly_studproject.R;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.AuthManager;
import com.lanit_tercom.dogfriendly_studproject.data.auth_manager.firebase_impl.AuthManagerFirebaseImpl;
import com.lanit_tercom.dogfriendly_studproject.data.executor.JobExecutor;
import com.lanit_tercom.dogfriendly_studproject.executor.UIThread;
import com.lanit_tercom.domain.executor.PostExecutionThread;
import com.lanit_tercom.domain.executor.ThreadExecutor;
import com.lanit_tercom.library.data.manager.NetworkManager;
import com.lanit_tercom.library.data.manager.impl.NetworkManagerImpl;


public class InvitationFragment extends BaseFragment {

    //ThumbTextSeekBar thumbTextSeekBar;
    private TextView radiusTextView;
    private SeekBar radiusSeekBar;

    public InvitationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invitation, container, false);

        /*humbTextSeekBar = new ThumbTextSeekBar(getContext());
        thumbTextSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                thumbTextSeekBar.setThumbText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //thumbTextSeekBar.showTxt();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //thumbTextSeekBar.hideTxt();
            }
        });
        */
        initSeekBarText(view);

        return view;
    }

    @Override
    public void initializePresenter() {
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        AuthManager authManager = new AuthManagerFirebaseImpl();
        NetworkManager networkManager = new NetworkManagerImpl(getContext());

    }

    private void initSeekBarText(View view){
        radiusTextView = view.findViewById(R.id.tv_thumb);
        radiusSeekBar = view.findViewById(R.id.sb_radius);

        radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radiusTextView.setText(progress + " км");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /*public class ThumbTextView extends androidx.appcompat.widget.AppCompatTextView {

        private LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        private int width = 0;

        public ThumbTextView(Context context) {
            super(context);
        }

        public ThumbTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void attachToSeekBar(SeekBar seekBar) {
            String content = getText().toString();
            if (TextUtils.isEmpty(content) || seekBar == null)
                return;
            float contentWidth = this.getPaint().measureText(content);
            int realWidth = width - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
            int maxLimit = (int) (width - contentWidth - seekBar.getPaddingRight());
            int minLimit = seekBar.getPaddingLeft();
            float percent = (float) (1.0 * seekBar.getProgress() / seekBar.getMax());
            int left = minLimit + (int) (realWidth * percent - contentWidth / 2.0);
            left = left <= minLimit ? minLimit : left >= maxLimit ? maxLimit : left;
            lp.setMargins(left, 0, 0, 0);
            setLayoutParams(lp);

        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (width == 0)
                width = MeasureSpec.getSize(widthMeasureSpec);
        }
    }


    public class ThumbTextSeekBar extends LinearLayout {

        public ThumbTextView tvThumb;
        public SeekBar seekBar;
        private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener;

        public ThumbTextSeekBar(Context context) {
            super(context);
            init();
        }

        public ThumbTextSeekBar(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            LayoutInflater.from(getContext()).inflate(R.layout.view_thumb_text_seekbar, this);
            setOrientation(LinearLayout.VERTICAL);
            tvThumb = (ThumbTextView) findViewById(R.id.tv_thumb);
            seekBar = (SeekBar) findViewById(R.id.sb_radius);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (onSeekBarChangeListener != null)
                        onSeekBarChangeListener.onStopTrackingTouch(seekBar);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    if (onSeekBarChangeListener != null)
                        onSeekBarChangeListener.onStartTrackingTouch(seekBar);
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (onSeekBarChangeListener != null)
                        onSeekBarChangeListener.onProgressChanged(seekBar, progress, fromUser);
                    tvThumb.attachToSeekBar(seekBar);
                }
            });

        }

        public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener l) {
            this.onSeekBarChangeListener = l;
        }

        public void setThumbText(String text) {
            tvThumb.setText(text);
        }

        public void setProgress(int progress) {
            if (progress == seekBar.getProgress() && progress == 0) {
                seekBar.setProgress(1);
                seekBar.setProgress(0);
            } else {
                seekBar.setProgress(progress);
            }
        }
    }*/
}