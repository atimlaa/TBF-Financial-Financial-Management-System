/*
 * This claas is just a node pointer
 */
package com.tbf;

public class PortfolioNode<T>
{
	private PortfolioNode<T> next;
	private T item ;

	public PortfolioNode(T item)
	{
		this.item = item;
		this.next = null;
	}

	public T getPortfolio()
	{
		return item;
	}

	public PortfolioNode<T> getNext()
	{
		return next;
	}

	public void setNext(PortfolioNode<T> next)
	{
		this.next = next;
	}

}
