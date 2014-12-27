/*
 *
 * This file is part of FileExplorer.
 *
 * FileExplorer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FileExplorer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 */

package com.talent.taskmanager.task;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.talent.taskmanager.R;
import com.coal.black.bc.socket.dto.TaskDto;

public class TaskDetailDialog extends AlertDialog {
    private TaskDto mTask;
    private Context mContext;
    private View mView;

    public TaskDetailDialog(Context context, TaskDto task) {
        super(context);
        mTask = task;
        mContext = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        mView = getLayoutInflater().inflate(R.layout.task_detail_dialog, null);
        //setIcon(R.drawable.ic_launcher);
        setTitle(mTask.getVisitReason());

        ((TextView) mView.findViewById(R.id.detail_person_name))
                .setText(mTask.getName());
        ((TextView) mView.findViewById(R.id.detail_task_address))
                .setText(mTask.getAddress());
        ((TextView) mView.findViewById(R.id.detail_task_contact_info)).setText(mTask.getContactInfo());
        ((TextView) mView.findViewById(R.id.detail_task_identity_card)).setText(mTask.getIdentityCard());
        ((TextView) mView.findViewById(R.id.detail_task_bank_card)).setText(mTask.getBankCard());
        ((TextView) mView.findViewById(R.id.detail_task_case_amount)).setText(Double.toString(mTask.getCaseAmount()));
        ((TextView) mView.findViewById(R.id.detail_task_has_payed)).setText(Double.toString(mTask.getHasPayed()));

        setView(mView);
        setButton(BUTTON_NEGATIVE, mContext.getString(R.string.confirm_know), (DialogInterface.OnClickListener) null);

        super.onCreate(savedInstanceState);
    }

}
