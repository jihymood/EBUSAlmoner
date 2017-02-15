package com.xpro.ebusalmoner.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xpro.ebusalmoner.R;


public class CustomDialog {

	private Context mContext;

	private Dialog mDialog;

	private View mProgressContainer;
	private TextView mProgressMessageView;

	private View mTitleContainer;
	private ImageView mTitleIcon;
	private TextView mTitleView;

	private View mContentContainer;
	private ListView mList;
	private View mMessageContainer;
	private TextView mMessageTextView;

	private View mButtonsContainer;
	private Button mButton1;
	private Button mButton2;
	private Button mButton3;
	private View mButtonSpace1;
	private View mButtonSpace2;
	private int layoutResourceId;
	private int mCheckedItem = -1;

	private FrameLayout mCustomViewContainer;

	public CustomDialog(Context context) {
		mDialog = new Dialog(context, R.style.CustomDialog);
		mContext = context;
		onCreate();
	}

	public CustomDialog(Context context, boolean isTrans) {
		mDialog = new Dialog(context, R.style.CustomDialogTrans);
		mContext = context;
		onCreate();
	}

	public void show() {
		if(!mDialog.isShowing()) {
			mDialog.show();
		}
	}

	public void dismiss() {
		if(mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}

	public Dialog getDialog() {
		return mDialog;
	}

	public CustomDialog setCanceledOnTouchOutside(boolean cancel) {
		mDialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	public CustomDialog setCancelable(boolean cancelable) {
		mDialog.setCancelable(cancelable);
		return this;
	}

	public CustomDialog setOnCancelListener(DialogInterface.OnCancelListener listener) {
		mDialog.setOnCancelListener(listener);
		return this;
	}

	public CustomDialog setOnDismissListener(DialogInterface.OnDismissListener listener) {
		mDialog.setOnDismissListener(listener);
		return this;
	}

	public View findViewById(int id) {
		return mDialog.findViewById(id);
	}

	protected Context getContext() {
		return mDialog.getContext();
	}

	protected void onCreate() {
		mDialog.setContentView(R.layout.custom_dialog_layout);

		mProgressContainer = findViewById(R.id.dialog_progress_container);
		mProgressMessageView = (TextView) findViewById(R.id.dialog_loading_message);

		mTitleContainer = findViewById(R.id.dialog_title_container);
		mTitleIcon = (ImageView) findViewById(R.id.dialog_icon);
		mTitleView = (TextView) findViewById(R.id.dialog_title);

		mContentContainer = findViewById(R.id.dialog_content_container);
		mList = (ListView) findViewById(R.id.dialog_content_listview);
		mMessageContainer = findViewById(R.id.dialog_content_message);
		mMessageTextView = (TextView) findViewById(R.id.dialog_content_message_text);

		mButtonsContainer = findViewById(R.id.dialog_buttons_container);
		mButton1 = (Button) findViewById(R.id.dialog_button1);
		mButton2 = (Button) findViewById(R.id.dialog_button2);
		mButton3 = (Button) findViewById(R.id.dialog_button3);
		mButtonSpace1 = findViewById(R.id.dialog_space1);
		mButtonSpace2 = findViewById(R.id.dialog_space2);

		mCustomViewContainer = (FrameLayout) findViewById(R.id.dialog_custom_view_container);
	}

	public CustomDialog setCustomView(int layoutId) {
		LayoutInflater layoutInfalter = LayoutInflater.from(mContext);
		mCustomViewContainer.removeAllViews();
		mContentContainer.setVisibility(View.VISIBLE);
		mCustomViewContainer.setVisibility(View.VISIBLE);
		mMessageContainer.setVisibility(View.GONE);
		mList.setVisibility(View.GONE);
		layoutInfalter.inflate(layoutId, mCustomViewContainer, true);
		return this;
	}

	public CustomDialog setCustomView(View view) {
		mCustomViewContainer.removeAllViews();
		mContentContainer.setVisibility(View.VISIBLE);
		mCustomViewContainer.setVisibility(View.VISIBLE);
		mMessageContainer.setVisibility(View.GONE);
		mList.setVisibility(View.GONE);
		mCustomViewContainer.addView(view);
		return this;
	}

	public CustomDialog setProgressMessage(String message) {
		mProgressContainer.setVisibility(View.VISIBLE);
		mContentContainer.setVisibility(View.GONE);
		mProgressMessageView.setText(message);
		return this;
	}

	public CustomDialog setProgressMessage(int resId) {
		setProgressMessage(getContext().getString(resId));
		return this;
	}

	public CustomDialog setTitle(int resId) {
		setTitle(getContext().getString(resId));
		return this;
	}

	public CustomDialog setTitle(String title) {
		if(mTitleContainer.getVisibility() != View.VISIBLE) {
			mTitleContainer.setVisibility(View.VISIBLE);
		}
		mTitleView.setText(title);
		return this;
	}

	public CustomDialog setTitleIcon(int resId) {
		if(mTitleContainer.getVisibility() != View.VISIBLE) {
			mTitleContainer.setVisibility(View.VISIBLE);
		}

		mTitleIcon.setImageResource(resId);
		return this;
	}

	public CustomDialog setTitleIcon(Drawable drawable) {
		if(mTitleContainer.getVisibility() != View.VISIBLE) {
			mTitleContainer.setVisibility(View.VISIBLE);
		}

		mTitleIcon.setImageDrawable(drawable);
		return this;
	}

	public CustomDialog setMessage(String message) {
		if(mProgressContainer.getVisibility() != View.GONE) {
			mProgressContainer.setVisibility(View.GONE);
		}

		if(mContentContainer.getVisibility() != View.VISIBLE) {
			mContentContainer.setVisibility(View.VISIBLE);
		}

		if(mMessageContainer.getVisibility() != View.VISIBLE) {
			mMessageContainer.setVisibility(View.VISIBLE);
		}
		if(mList.getVisibility() != View.GONE) {
			mList.setVisibility(View.GONE);
		}
		mMessageTextView.setText(message);
		return this;
	}

	public CustomDialog setMessage(int resId) {
		setMessage(getContext().getString(resId));
		return this;
	}

	public CustomDialog setPositiveButton(int resId, final OnClickListener listener) {
		setPositiveButton(getContext().getString(resId), listener);
		return this;
	}

	public CustomDialog setPositiveButton(CharSequence text, final OnClickListener listener) {
		if(mButtonsContainer.getVisibility() != View.VISIBLE) {
			mButtonsContainer.setVisibility(View.VISIBLE);
		}

		if(mButton1.getVisibility() != View.VISIBLE) {
			mButton1.setVisibility(View.VISIBLE);
		}

		if(mButton2.getVisibility() == View.VISIBLE && mButtonSpace1.getVisibility() != View.VISIBLE) {
			mButtonSpace1.setVisibility(View.VISIBLE);
		}

		if(mButton3.getVisibility() == View.VISIBLE && mButtonSpace2.getVisibility() != View.VISIBLE) {
			mButtonSpace2.setVisibility(View.VISIBLE);
		}

		mButton1.setText(text);
		mButton1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(listener != null) {
					listener.onClick(mDialog, 0);
				} else {
					dismiss();
				}
			}
		});
		return this;
	}

	public CustomDialog setNeutralButton(int resId, final OnClickListener listener) {
		return setNeutralButton(getContext().getString(resId), listener);
	}

	public CustomDialog setNeutralButton(CharSequence text, final OnClickListener listener) {
		if(mButtonsContainer.getVisibility() != View.VISIBLE) {
			mButtonsContainer.setVisibility(View.VISIBLE);
		}

		if(mButtonSpace1.getVisibility() != View.VISIBLE && mButton1.getVisibility() == View.VISIBLE) {
			mButtonSpace1.setVisibility(View.VISIBLE);
		}

		if(mButton2.getVisibility() != View.VISIBLE) {
			mButton2.setVisibility(View.VISIBLE);
		}

		mButton2.setText(text);
		mButton2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(listener != null) {
					listener.onClick(mDialog, 1);
				} else {
					dismiss();
				}
			}
		});
		return this;
	}

	public CustomDialog setNegativeButton(int resId, final OnClickListener listener) {
		return setNegativeButton(getContext().getString(resId), listener);
	}

	public CustomDialog setNegativeButton(CharSequence text, final OnClickListener listener) {
		if(mButtonsContainer.getVisibility() != View.VISIBLE) {
			mButtonsContainer.setVisibility(View.VISIBLE);
		}

		if(mButton3.getVisibility() != View.VISIBLE) {
			mButton3.setVisibility(View.VISIBLE);
		}

		if((mButton2.getVisibility() == View.VISIBLE || mButton1.getVisibility() == View.VISIBLE)
				&& mButtonSpace2.getVisibility() != View.VISIBLE) {
			mButtonSpace2.setVisibility(View.VISIBLE);
		}

		mButton3.setText(text);
		mButton3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(listener != null) {
					listener.onClick(mDialog, 2);
				} else {
					dismiss();
				}
			}
		});
		return this;
	}

	public CustomDialog setAdapter(ListAdapter adapter, final OnClickListener listener) {
		if(mContentContainer.getVisibility() != View.VISIBLE) {
			mContentContainer.setVisibility(View.VISIBLE);
		}

		if(mList.getVisibility() != View.VISIBLE) {
			mList.setVisibility(View.VISIBLE);
		}

		if(mMessageContainer.getVisibility() != View.GONE) {
			mMessageContainer.setVisibility(View.GONE);
		}

		mList.setAdapter(adapter);
		mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				if(listener != null) {
					listener.onClick(mDialog, position);
				}
			}
		});
		return this;
	}

	public CustomDialog setItems(String[] items, OnClickListener listener) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
				R.layout.custom_dialog_list_item, R.id.item_text, items);
		return setAdapter(adapter, listener);
	}

	public CustomDialog setItems(int resId, OnClickListener listener) {
		return setItems(getContext().getResources().getStringArray(resId), listener);
	}

	public CustomDialog setSingleChoiceItems(String[] items, final int checkedItem, final OnClickListener listener) {
		mCheckedItem = checkedItem;
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
				R.layout.custom_dialog_list_single_choice_item, R.id.item_text, items) {

			@Override
			public View getView(final int position, View convertView,
								ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				CheckBox checkBox = (CheckBox) view.findViewById(R.id.item_checkbox);

				view.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						mCheckedItem = position;
						notifyDataSetChanged();
					}
				});

				if(position == mCheckedItem) {
					checkBox.setChecked(true);
				} else {
					checkBox.setChecked(false);
				}

				return view;
			}
		};

		if(mContentContainer.getVisibility() != View.VISIBLE) {
			mContentContainer.setVisibility(View.VISIBLE);
		}

		if(mList.getVisibility() != View.VISIBLE) {
			mList.setVisibility(View.VISIBLE);
		}

		if(mMessageContainer.getVisibility() != View.GONE) {
			mMessageContainer.setVisibility(View.GONE);
		}

		mList.setAdapter(adapter);
		mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				if(listener != null) {
					listener.onClick(mDialog, position);
				}
			}
		});
		return this;
	}

	public CustomDialog setSingleChoiceItems(int itemsId, final int checkedItem, final OnClickListener listener) {
		return setSingleChoiceItems(getContext().getResources().getStringArray(itemsId), checkedItem, listener);
	}

	public int getSingleChoicePosition() {
		return mCheckedItem;
	}
}
