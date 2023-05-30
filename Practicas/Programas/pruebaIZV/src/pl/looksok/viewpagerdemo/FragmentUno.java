package pl.looksok.viewpagerdemo;

	import android.os.Bundle;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	import android.support.v4.app.Fragment;

public class FragmentUno extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.intro, container, false);
		return view;
	}
}
