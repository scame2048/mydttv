package dai.android.learn.dttv.app.ijk;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public interface IIListener extends
        IMediaPlayer.OnCompletionListener,
        IMediaPlayer.OnSeekCompleteListener,
        IMediaPlayer.OnPreparedListener,
        IMediaPlayer.OnBufferingUpdateListener {
}
