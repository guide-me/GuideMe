package org.guideme.guideme.ui;

import org.eclipse.swt.widgets.Composite;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurface;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapter;

/**
 * Implementation of a video surface component that uses an SWT Composite.
 * <p>
 * <em>This class might get added to a future version of vlcj.</em>
 */
public class CompositeVideoSurface extends VideoSurface {

    /**
	 * 
	 */
	private static final long serialVersionUID = -602915292932819950L;
	private final Composite composite;

    public CompositeVideoSurface(Composite composite, VideoSurfaceAdapter videoSurfaceAdapter) {
        super(videoSurfaceAdapter);
        this.composite = composite;
    }

    @Override
    public void attach(LibVlc libvlc, MediaPlayer mediaPlayer) {
        long componentId = composite.handle;
        videoSurfaceAdapter.attach(libvlc, mediaPlayer, componentId);
    }
}
