package com.talent.taskmanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.talent.taskmanager.network.NetworkState;
import com.coal.black.bc.socket.client.handlers.UserLoginHandler;
import com.coal.black.bc.socket.client.returndto.LoginResult;

import de.greenrobot.event.EventBus;


public class LoginActivity extends Activity {

    private static final int LOGIN_SUCCESS = 0;
    private static final int LOGIN_FAIL_WRONG_INFO = 1;
    private static final int LOGIN_FAIL_BAD_NETWORK = 2;
    private EditText mUsernameTextView;
    private EditText mPasswordTextView;
    private Button mLoginButton;
    private TextWatcher mTextWatcher;
    private Toast mToast = null;
    private String mUsername;
    private String mPassword;
    private ProgressDialog mProcessingDialog;
    private LoginResult mLoginResult;
    private int mUserID;
    private EventBus mEventBus = EventBus.getDefault();
    private Handler mLoginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            hideProcessDialog();
            if (mLoginResult.isSuccess()) {
//                Log.d("acmllaugh1", "login (line 27): login user id : " + mLoginResult.getUserId());
                mUserID = mLoginResult.getUserId();
                saveUserID();
                startTaskListActivity(mUserID);
            } else {
                if (mLoginResult.isBusException()) {
                    Log.d("acmllaugh1", "login (line 32): bussiness exception : " + mLoginResult.getBusinessErrorCode());
                    Utils.showToast(mToast, getString(R.string.login_fail_wrong_info), LoginActivity.this);
                } else {
                    Log.d("acmllaugh1", "login (line 34): other exception : " + mLoginResult.getThrowable());
                }
            }
        }
    };



    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.registerToEventBus(this);
        checkNetWorkConnected();
        if (getSavedUserID() == -1) { // -1 means no saved user id was found.
            setContentView(R.layout.activity_login);
            checkUpdate();
            initMembers();
        }else{
            startTaskListActivity(mUserID);
            this.finish();
        }
    }

    private void checkNetWorkConnected() {
        //Check if there is an available network.
        NetworkState state = Utils.getCurrentNetworkState(getApplicationContext());
        if (!state.isConnected()) {
            Utils.showToast(mToast, getString(R.string.net_work_unavailable), this);
        }
    }

    private void saveUserID() {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt(Constants.SAVED_USER_ID, mUserID);
        editor.apply();
    }

    private int getSavedUserID() {
        mPrefs = getSharedPreferences(Constants.TASK_MANAGER, MODE_PRIVATE);
        mUserID = mPrefs.getInt(Constants.SAVED_USER_ID, -1);
        return mUserID;
    }

    /**
     *
     */
    private void initMembers() {
        mUsernameTextView = (EditText) findViewById(R.id.txt_username_input);
        mPasswordTextView = (EditText) findViewById(R.id.txt_password_input);
        mLoginButton = (Button) findViewById(R.id.btn_login);
        addListener(mUsernameTextView.getId());
        addListener(mPasswordTextView.getId());
        addListener(mLoginButton.getId());
    }

    private void addListener(int id) {
        switch (id) {
            case R.id.txt_username_input:
                break;
            case R.id.txt_password_input:
                break;
            case R.id.btn_login:
                mLoginButton.setOnClickListener(getLoginButtonListener());

        }
    }

    private View.OnClickListener getLoginButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToLogin();
            }
        };
    }

    private void tryToLogin() {
        //Check if username and password is valid and do login.
        boolean inputCheckResult = checkInput();
        if (inputCheckResult) {
            doLogin();
        }
    }

    private void doLogin() {
        // Connect to server and try to login.
        if (mUsername != null && mPassword != null) {
            showProcessDialog();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    UserLoginHandler userLogin = new UserLoginHandler();
                    mLoginResult = userLogin.login(mUsername, mPassword);
                    mLoginHandler.sendMessage(new Message());
                }
            });
            thread.start();
        } else {
            Log.d("acmllaugh1", "doLogin (line 80): mUsername == null or mPassword == null.");
        }
    }

    private void showProcessDialog() {
        if (mProcessingDialog == null) {
            mProcessingDialog = ProgressDialog.show(this, null, getString(R.string.please_wait));
            mProcessingDialog.setCancelable(false);
        } else if (!mProcessingDialog.isShowing()) {
            mProcessingDialog.show();
        }
    }

    private void hideProcessDialog() {
        if (mProcessingDialog != null && mProcessingDialog.isShowing()) {
            mProcessingDialog.dismiss();
        }
    }

    private boolean checkInput() {
        Editable username = mUsernameTextView.getText();
        Editable password = mPasswordTextView.getText();
        if (username != null && password != null) {
            if (username.toString().isEmpty()) {
                Utils.showToast(mToast, getString(R.string.username_empty_hint), this);
                return false;
            }
            if (password.toString().isEmpty()) {
                Utils.showToast(mToast, getString(R.string.password_empty_hint), this);
                return false;
            }
            //TODO: Check if username and password is not enough length and if contains invaild chars.
            mUsername = username.toString();
            mPassword = password.toString();
            return true;
        } else {
            Log.d("acmllaugh1", "checkInput (line 86): username or password is null.");
        }
        return false;
    }


    private void checkUpdate() {
        //TODO : Check if there is a new update.
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    private void startTaskListActivity(int userid) {
        Intent intent = new Intent(this, TaskListActivity.class);
        intent.setAction(Constants.START_TASK_LIST_ACTIVITY_ACTION);
        intent.putExtra(Constants.USER_ID, userid);
        startActivity(intent);
        this.finish();
    }

    public void onEvent(NetworkState state) {
        if (!state.isConnected()) {
            Utils.showToast(mToast, getString(R.string.net_work_unavailable), this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Utils.unRegisterEventBus(this);
    }
}
