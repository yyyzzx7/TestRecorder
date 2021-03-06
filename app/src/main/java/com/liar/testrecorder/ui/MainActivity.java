package com.liar.testrecorder.ui;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.liar.testrecorder.App;
import com.liar.testrecorder.BuildConfig;
import com.liar.testrecorder.R;
import com.liar.testrecorder.config.Constant;
import com.liar.testrecorder.event.RecorderEvent;
import com.liar.testrecorder.recorder.RecordHelper;
import com.liar.testrecorder.recorder.RecordManager;
import com.liar.testrecorder.recorder.listener.RecordStateListener;
import com.liar.testrecorder.ui.dialog.InputFileNameDialog;
import com.liar.testrecorder.utils.TimeUtils;
import com.liar.testrecorder.utils.file.FileManager;
import com.liar.testrecorder.utils.http.HttpFunction;
import com.liar.testrecorder.widget.AudioView;
import com.lodz.android.component.base.activity.BaseActivity;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.FileUtils;
import com.lodz.android.core.utils.NotificationUtils;
import com.lodz.android.core.utils.ToastUtils;
import com.zlw.main.recorderlib.recorder.RecordConfig;
import com.zlw.main.recorderlib.recorder.listener.RecordFftDataListener;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;
import com.zlw.main.recorderlib.recorder.listener.RecordSoundSizeListener;
import com.zlw.main.recorderlib.utils.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    /**?????? **/
    @BindView(R.id.btRecord)
    Button btRecord;
//    /**?????? **/
//    @BindView(R.id.btStop)
//    Button btStop;

    /**?????? **/
    @BindView(R.id.btFinish)
    Button btFinish;

    /**?????? **/
    @BindView(R.id.tvState)
    TextView tvState;
    /**???????????? **/
    @BindView(R.id.tvSoundSize)
    TextView tvSoundSize;
//    /**RadioGroup ???????????? **/
//    @BindView(R.id.rgAudioFormat)
//    RadioGroup rgAudioFormat;
//    /**RadioGroup ???????????????**/
//    @BindView(R.id.rgSimpleRate)
//    RadioGroup rgSimpleRate;
//    /**RadioGroup ????????????**/
//    @BindView(R.id.tbEncoding)
//    RadioGroup tbEncoding;
    /**AudioView **/
    @BindView(R.id.audioView)
    AudioView audioView;
//    /**???????????????(???)**/
//    @BindView(R.id.spUpStyle)
//    Spinner spUpStyle;
//    /**???????????????(???) **/
//    @BindView(R.id.spDownStyle)
//    Spinner spDownStyle;


    /**?????????????????? **/
    @BindView(R.id.tvRecordTime)
    TextView tvRecordTime;

    //??????UI????????????
    private long duration = 1000L;
    //??????
    private long durationDelay = 50L;
    //????????????
    private long  timeCounter = 0L;

    //????????????
    private int  mSoundSize = 0;

    private boolean isStart = false;
    private boolean isPause = false;
    private boolean isSave = false;


    final RecordManager recordManager = RecordManager.getInstance();

    private static final String[] STYLE_DATA = new String[]{"STYLE_ALL", "STYLE_NOTHING", "STYLE_WAVE", "STYLE_HOLLOW_LUMP"};

    /**
     * ??????PendingIntent?????????
     * @param context ?????????
     * @param data ??????
     */
    public static PendingIntent startPendingIntent(Context context,String data) {
        Intent intent =  new Intent(context, MainActivity.class);
        intent.putExtra(Constant.EXTRA_MSG_DATA, data);
        return PendingIntent.getActivity(context, UUID.randomUUID().hashCode(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    /**
     * ??????
     * @param context ?????????
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initTitleBar(getTitleBarLayout());
        showStatusCompleted();
    }

    /**
     * ?????????TitleBar
     */
    private void initTitleBar(TitleBarLayout titleBarLayout) {
        titleBarLayout.setTitleName("????????????Minus");
        titleBarLayout.needBackButton(false);
    }


    @Override
    protected void initData() {
        super.initData();
        initAudioView();
        initEvent();
        initRecord();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRecordEvent();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        /*???????????? */
        btRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPlay();
            }
        });
//        /*????????????*/
//        btStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isSave=false;
//                doStop();
//            }
//        });

        /*????????????*/
        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complete();
            }
        });


//        /**???????????? ??????**/
//        rgAudioFormat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rbPcm:
//                        //PCM??????
//                        recordManager.changeFormat(RecordConfig.RecordFormat.PCM);
//                        break;
//                    case R.id.rbMp3:
//                        //Mp3??????
//                        recordManager.changeFormat(RecordConfig.RecordFormat.MP3);
//                        break;
//                    case R.id.rbWav:
//                        //Wav??????
//                        recordManager.changeFormat(RecordConfig.RecordFormat.WAV);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//
//        /**???????????????  ??????**/
//        rgSimpleRate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb8K:
//                        //8k?????????
//                        recordManager.changeRecordConfig(recordManager.getRecordConfig().setSampleRate(8000));
//                        break;
//                    case R.id.rb16K:
//                        //16K?????????
//                        recordManager.changeRecordConfig(recordManager.getRecordConfig().setSampleRate(16000));
//                        break;
//                    case R.id.rb44K:
//                        //44K?????????
//                        recordManager.changeRecordConfig(recordManager.getRecordConfig().setSampleRate(44100));
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//
//        /**???????????? ??????**/
//        tbEncoding.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb8Bit:
//                        //8Bit??????
//                        recordManager.changeRecordConfig(recordManager.getRecordConfig().setEncodingConfig(AudioFormat.ENCODING_PCM_8BIT));
//                        break;
//                    case R.id.rb16Bit:
//                        //16Bit??????
//                        recordManager.changeRecordConfig(recordManager.getRecordConfig().setEncodingConfig(AudioFormat.ENCODING_PCM_16BIT));
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });

//        /**??????????????? ??? ????????????**/
//        spUpStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                audioView.setStyle(AudioView.ShowStyle.getStyle(STYLE_DATA[position]), audioView.getDownStyle());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        /**??????????????? ??? ????????????**/
//        spDownStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                audioView.setStyle(audioView.getUpStyle(), AudioView.ShowStyle.getStyle(STYLE_DATA[position]));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

    /**?????????AudioView**/
    private void initAudioView() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, STYLE_DATA);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spUpStyle.setAdapter(adapter);
//        spDownStyle.setAdapter(adapter);
    }
    private void initEvent() {
        //16K?????????
        recordManager.changeRecordConfig(recordManager.getRecordConfig().setSampleRate(16000));
        //16Bit??????
        recordManager.changeRecordConfig(recordManager.getRecordConfig().setEncodingConfig(AudioFormat.ENCODING_PCM_16BIT));
    }
    /**?????????RecordManager**/
    private void initRecord() {
        recordManager.init(App.getInstance(), BuildConfig.DEBUG);
//        recordManager.changeFormat(RecordConfig.RecordFormat.WAV);
        //Mp3??????
        recordManager.changeFormat(RecordConfig.RecordFormat.MP3);
//        recordManager.changeRecordDir(recordDir);
        //????????????
//        String recordDir = String.format(Locale.getDefault(), "%s/Record/Test/",
//                Environment.getExternalStorageDirectory().getAbsolutePath());
        String recordDir = FileManager.getAudioFolderPath();
        recordManager.changeRecordDir(recordDir);
        initRecordEvent();
    }


    /**RecordManager??????**/
    private void initRecordEvent() {
        recordManager.setRecordStateListener(new RecordStateListener() {
            @Override
            public void onStateChange(RecordHelper.RecordState state) {
                Logger.i(TAG, "onStateChange %s", state.name());

                switch (state) {
                    case PAUSE:
                        tvState.setText("?????????");
                        break;
                    case IDLE:
                        tvState.setText("?????????");
                        break;
                    case RECORDING:
                        tvState.setText("?????????");
                        break;
                    case STOP:
                        tvState.setText("??????");
                        break;
                    case FINISH:
                        tvState.setText("????????????");
                        tvSoundSize.setText("---");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onError(String error) {
                Logger.i(TAG, "onError %s", error);
            }
        });
        recordManager.setRecordSoundSizeListener(new RecordSoundSizeListener() {
            @Override
            public void onSoundSize(int soundSize) {
                mSoundSize=soundSize;
                tvSoundSize.setText(String.format(Locale.getDefault(), "???????????????%s db", soundSize));
            }
        });


        recordManager.setRecordResultListener(new RecordResultListener() {
            @Override
            public void onResult(File result) {

//                 Toast.makeText(MainActivity.this, "??????????????? " + result.getAbsolutePath(), Toast.LENGTH_SHORT).show();

                if(isSave){//??????
                    // showInputFileNameDialog(result);
                }else {//??????????????????????????????
                    FileUtils.delFile(result.getAbsolutePath());
                }
            }
        });
        recordManager.setRecordFftDataListener(new RecordFftDataListener() {
            @Override
            public void onFftData(byte[] data) {
                audioView.setWaveData(data);
            }
        });
    }

    //?????????????????? Dialog
    private void showInputFileNameDialog(File result){
        InputFileNameDialog dialog =new InputFileNameDialog(getContext());
        dialog.setListener(new InputFileNameDialog.Listener() {
            @Override
            public void onCancel(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onConfirm(Dialog dialog, String fileName) {
                //???????????????????????????????????????????????????????????????
                if(FileUtils.isFileExists(
                        FileManager.getAudioFolderPath()+fileName+FileUtils.getSuffix(result.getAbsolutePath()))){

                    showCmDialog(result,fileName);
                }else {
                    //?????????????????????
                    if(FileUtils.renameFile(result.getAbsolutePath(),
                            fileName+FileUtils.getSuffix(result.getAbsolutePath()))){
                        ToastUtils.showShort(getContext(),"????????????,??????????????????"+FileManager.getAudioFolderPath());
                    }else {
                        ToastUtils.showShort(getContext(),"????????????");
                    }

                }
                dialog.dismiss();
                isSave=false;
            }
        });
        dialog.show();
    }

    /**????????????Dialog **/
    public void showCmDialog(File result,String fileName){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("?????????");
        builder.setMessage("????????????????????????????????????????????????????????????");
        builder.setIcon(R.drawable.ic_launcher);
        builder.setCancelable(true);            //??????????????????????????????????????????????????????
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog1, int which) {
                if(FileUtils.renameFile(result.getAbsolutePath(),
                        fileName+FileUtils.getSuffix(result.getAbsolutePath()))){
                    ToastUtils.showShort(getContext(),"????????????");

                }else {
                    ToastUtils.showShort(getContext(),"????????????");
                }
                dialog1.dismiss();
            }
        });
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog1, int which) {
                dialog1.dismiss();
                return;
            }
        });
    }

    /**??????  ?????? **/
    private void doStop() {
        recordManager.stop();
        btRecord.setText("????????????");
        isPause = false;
        isStart = false;
        timeCounter = 0;
        tvRecordTime.setText
                ("00:00:00");
        NotificationUtils.create(getContext()).getManager().cancel(Constant.NOTIFI_RECORDER_ID);

    }

    /**???????????????  ?????? **/
    private void doPlay() {
        if (isStart) {
            recordManager.pause();
            btRecord.setText("????????????");
            isPause = true;
            isStart = false;

        } else {
            if (isPause) {
                recordManager.resume();
            } else {
                recordManager.start();
            }
            btRecord.setText("????????????");
            isStart = true;
        }
//        showCustomNotify(TimeUtils.getGapTime(timeCounter));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeCounter = 0;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    /** ??????Intent */
    private void handleIntent(Intent intent) {
        if (intent == null){
            return;
        }
        String data= intent.getStringExtra(Constant.EXTRA_MSG_DATA);
        if (TextUtils.isEmpty(data)){
            return;
        }
        //????????? ?????????????????? ??????
        if (data.equals(Constant.NOTIFI_FINISH_MSG)){
            complete();
            return;
        }
    }

    //??????????????????
    private void complete(){
        isSave=true;
        doStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  onRecorderEvent(RecorderEvent event){
        //??????????????????????????? eventbus
        if(event==null){
            return;
        }

        tvSoundSize.setText(String.format(Locale.getDefault(), "???????????????%s db", event.soundSize));
        tvRecordTime.setText(TimeUtils.getGapTime(event.timeCounter));

        if(!TextUtils.isEmpty(event.type)){
            doPlay();
            return;
        }

    }

}
