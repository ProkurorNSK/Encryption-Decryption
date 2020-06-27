import java.util.Arrays;

class AsciiCharSequence implements CharSequence {
    private byte[] data;

    public AsciiCharSequence(byte[] data) {
        this.data = data;
    }

    @Override
    public int length() {
        return data.length;
    }

    @Override
    public char charAt(int i) {
        return (char) data[i];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return new AsciiCharSequence(Arrays.copyOfRange(data, start, end));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (byte b : data) {
            builder.append((char) b);
        }
        return builder.toString();
    }
}