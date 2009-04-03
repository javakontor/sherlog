package org.javakontor.sherlog.application.internal.heap;

/**
 * The HeapState contains information about the memory (consumption) of the current VM
 * 
 * @author Nils Hartmann
 * 
 */
public final class HeapState {

  private final long _usedMemory;

  private final long _totalMemory;

  private final long _maxMemory;

  /**
   * Create a new HeapState-Instance with the given values
   * 
   * @see #getCurrentHeapState()
   * 
   * @param usedMemory
   *          The currently allocated memory of the JavaVM (bytes)
   * @param totalMemory
   *          The currenty allocated memory (bytes)
   * @param maxMemory
   *          the max. avail. memory of the VM (in Bytes)
   */
  HeapState(long usedMemory, long totalMemory, long maxMemory) {
    this._usedMemory = usedMemory;
    this._totalMemory = totalMemory;
    this._maxMemory = maxMemory;
  }

  /**
   * 
   * @see #getUsedMemoryInMb()
   * @return
   */
  public long getUsedMemory() {
    return this._usedMemory;
  }

  /**
   * 
   * @see Runtime#totalMemory()
   * @see #getTotalMemoryInMb()
   * @see #getMaxMemory()
   * @return
   */
  public long getTotalMemory() {
    return this._totalMemory;
  }

  /**
   * 
   * @see #getUsedMemoryInMb()
   * @return
   */
  public long getUsedMemoryInMb() {
    return convertToMeg(this._usedMemory);
  }

  /**
   * 
   * @see Runtime#totalMemory()
   * @see #getMaxMemoryInMb()
   * @return
   */
  public long getTotalMemoryInMb() {
    return convertToMeg(this._totalMemory);
  }

  /**
   * 
   * @see Runtime#maxMemory()
   * @see #getTotalMemoryInMb()
   * @return
   */
  public long getMaxMemoryInMb() {
    return convertToMeg(this._maxMemory);
  }

  /**
   * 
   * @see Runtime#maxMemory()
   * @see #getTotalMemory()
   * @return
   */
  public long getMaxMemory() {
    return this._maxMemory;
  }

  /**
   * Converts the given number of bytes to the corresponding number of megabytes (rounded up).
   */
  private long convertToMeg(long numBytes) {
    return (numBytes + (512 * 1024)) / (1024 * 1024);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (convertToMeg(this._usedMemory) ^ (convertToMeg(this._usedMemory) >>> 32));
    result = prime * result + (int) (convertToMeg(this._totalMemory) ^ (convertToMeg(this._totalMemory) >>> 32));
    result = prime * result + (int) (convertToMeg(this._maxMemory) ^ (convertToMeg(this._maxMemory) >>> 32));
    return result;
  }

  /**
   * Two instances of HeapState are considered equal if their memory attributes (in megabyte) equals
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }

    final HeapState other = (HeapState) obj;

    final long currentMemMb = convertToMeg(this._usedMemory);
    final long otherCurrentMemMb = convertToMeg(other._usedMemory);
    if (currentMemMb != otherCurrentMemMb) {
      return false;
    }
    final long totalMemMb = convertToMeg(this._totalMemory);
    final long otherTotalMemMb = convertToMeg(other._totalMemory);
    if (totalMemMb != otherTotalMemMb) {
      return false;
    }

    final long maxMemMb = convertToMeg(this._maxMemory);
    final long otherMaxMemMb = convertToMeg(other._maxMemory);
    if (maxMemMb != otherMaxMemMb) {
      return false;
    }

    return true;
  }

  /**
   * Returns a snapshot of the current memory consumption of the VM
   * 
   * @return a HeapState with memory informations
   */
  public static HeapState getCurrentHeapState() {
    final Runtime runtime = Runtime.getRuntime();
    final long totalMemory = runtime.totalMemory();
    final long freeMemory = runtime.freeMemory();
    final long maxMemory = runtime.maxMemory();
    final long usedMemory = totalMemory - freeMemory;

    final HeapState heapState = new HeapState(usedMemory, totalMemory, maxMemory);

    return heapState;
  }

  @Override
  public String toString() {
    final StringBuffer buffer = new StringBuffer();
    buffer.append("[HeapState:");
    buffer.append(" _usedMemory: ");
    buffer.append(this._usedMemory);
    buffer.append(" _totalMemory: ");
    buffer.append(this._totalMemory);
    buffer.append(" _maxMemory: ");
    buffer.append(this._maxMemory);
    buffer.append("]");
    return buffer.toString();
  }

}
