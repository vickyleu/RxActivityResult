package app;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import io.victoralbertos.app.R;
import rx_activity_result2.RxActivityResult;

public class SampleFragment extends Fragment {

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sample_layout, container, false);
        return view;
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.bt_camera).setOnClickListener(view -> camera());
        getView().findViewById(R.id.bt_intent_sender).setOnClickListener(v -> intentSender());
    }

    private void camera() {
        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        RxActivityResult.on(this).startIntent(takePhoto)
               .subscribe(result -> {
                   Intent data = result.data();
                   int resultCode = result.resultCode();

                   if (resultCode == Activity.RESULT_OK) {
                       result.targetUI().showImage(data);
                   } else {
                       result.targetUI().printUserCanceled();
                   }
               });
    }

    private void showImage(Intent data) {
        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
        ((ImageView) getView().findViewById(R.id.iv_thumbnail)).setImageBitmap(imageBitmap);
    }

    private void intentSender() {
        Intent intent  = new Intent("sample.intentsender.intent.AN_INTENT");
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 1, intent, 0);

        RxActivityResult.on(this).startIntentSender(pendingIntent.getIntentSender(), new Intent(), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0))
                .subscribe(result -> {
                    if (result.resultCode() != Activity.RESULT_OK) result.targetUI().printUserCanceled();
                });
    }

    private void printUserCanceled() {
        Toast.makeText(getActivity(), getString(R.string.user_canceled_action), Toast.LENGTH_LONG).show();
    }

}
