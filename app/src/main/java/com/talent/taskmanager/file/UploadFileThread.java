package com.talent.taskmanager.file;

import android.os.Message;
import android.util.Log;

import com.coal.black.bc.socket.client.handlers.UploadFileHandler;
import com.coal.black.bc.socket.client.returndto.UploadFileResult;
import com.coal.black.bc.socket.dto.UploadFileDto;

import java.io.File;

/**
 * Created by chris on 14-12-30.
 */
public class UploadFileThread extends Thread {
    FileInfo mFileInfo = null;
    UploadResultListener mListener = null;

    public interface UploadResultListener {
        void onUploadSucceed(FileInfo fileInfo);
        void onUploadFailed(FileInfo fileInfo);
    }

    public UploadFileThread(FileInfo mFileInfo) {
        this.mFileInfo = mFileInfo;
    }

    public FileInfo getFileInfo() {
        return mFileInfo;
    }

    public void setFileInfo(FileInfo mFileInfo) {
        this.mFileInfo = mFileInfo;
    }

    public UploadResultListener getListener() {
        return mListener;
    }

    public void setListener(UploadResultListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void run() {
        if (mFileInfo != null)
            upLoadFile(mFileInfo);
    }

    public void upLoadFile(FileInfo fileInfo) {
        File f = new File(fileInfo.getFilePath());
        UploadFileDto fileDto = new UploadFileDto();
        fileDto.setClientFile(f);
        fileDto.setTaskId(fileInfo.getTaskId());
        fileDto.setPicture(fileInfo.isPicture());
        UploadFileHandler uh = new UploadFileHandler();
        UploadFileResult result = uh.upload(fileDto);
        Message msg = new Message();
        if (result.isSuccess()) {
            if (mListener != null) {
                mListener.onUploadSucceed(fileInfo);
            }
            Log.d("Chris", "upLoadFile, succeed");
        } else {
            if (mListener != null) {
                mListener.onUploadFailed(fileInfo);
            }
            if (result.isBusException()) {
                Log.d("Chris", "upLoadFile, Business Exception: " + result.getBusinessErrorCode());
            } else {
                Log.d("Chris", "upLoadFile, Other Exception: " + result.getThrowable());
            }
        }
    }
}
