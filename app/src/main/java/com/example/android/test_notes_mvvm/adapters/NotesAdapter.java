package com.example.android.test_notes_mvvm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.test_notes_mvvm.R;
import com.example.android.test_notes_mvvm.entities.Note;

public class NotesAdapter extends ListAdapter<Note, NotesAdapter.VH> {

    public interface OnNoteClickListener {
        void onNoteClicked(Note note);
    }
    private OnNoteClickListener listener;

    public NotesAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    public void setOnClickItem(OnNoteClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bindData(getItem(position));
    }


    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class VH extends RecyclerView.ViewHolder {
        private TextView mTitleTv;
        private TextView mDiscTv;
        private TextView mPriorityTv;

        public VH(@NonNull View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.note_title_tv);
            mDiscTv = itemView.findViewById(R.id.note_description_tv);
            mPriorityTv = itemView.findViewById(R.id.note_priority_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        Note note = getItem(position);
                        listener.onNoteClicked(note);
                    }
                }
            });
        }

        private void bindData(Note note) {
            mTitleTv.setText(note.getTitle());
            mDiscTv.setText(note.getDescription());
            mPriorityTv.setText(String.valueOf(note.getPriority()));
        }


    }


}
