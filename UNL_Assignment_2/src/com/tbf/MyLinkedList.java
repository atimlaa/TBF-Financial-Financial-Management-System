/*
 * This class is created to make our own new linked list and sort out our
 * portfolio with out using java library functions. I got help from lab 13 (i.e TruckList) and from 
 * professor bourke lecture and notes.
 */
package com.tbf;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T>
{
	private PortfolioNode<T> head;
	private int size;
	private Comparator<T> value;

	public MyLinkedList(Comparator<T> value)
	{
		this.head = null;
		this.size = 0;
		this.value = value;
	}

	public T getElementAtIndex(int index)
	{
		return getPortfolioNodeAtIndex(index).getPortfolio();
	}

	private PortfolioNode<T> getPortfolioNodeAtIndex(int index)
	{
		// Finds the node at the given index
		if (index < 0 || index >= this.size)
		{
			throw new IllegalArgumentException("Index our of bounds");

		}

		PortfolioNode<T> now = this.head;
		for (int i = 0; i < index; i++)
		{
			now = now.getNext();
		}
		return now;
	}

	public void insert(T item)
	{

		PortfolioNode<T> newNode = new PortfolioNode<T>(item);
		PortfolioNode<T> current = this.head;
		PortfolioNode<T> previous = null;

		if (size == 0)
		{
			this.head = newNode;
			size++;
		}

		else if (value.compare(newNode.getPortfolio(), this.head.getPortfolio()) >= 0)
		{
			PortfolioNode<T> temp = this.head;
			newNode.setNext(temp);
			this.head = newNode;
			size++;
		}

		else if (size != 0)
		{
			
			while (current != null && value.compare(item, current.getPortfolio()) < 0)
			{
				previous = current;
				current = current.getNext();
			}

			this.size++;

			newNode.setNext(current);
			previous.setNext(newNode);
		}

	}

	public T removeFromHead()
	{
		if (this.isEmpty())
		{
			throw new IllegalStateException("You cannot remove from an empty list");
		}
		PortfolioNode<T> item = this.head().getNext();
		this.size--;
		return null;
	}

	public int getSize()
	{
		return this.size;
	}

	private PortfolioNode<T> head()
	{
		return null;
	}

	private boolean isEmpty()
	{

		return (this.size == 0);
	}

	public T getNodeAtIndex(int i)
	{
		PortfolioNode<T> current = this.head;
		PortfolioNode<T> previous = null;

		for (int j = 0; j < i; j++)
		{
			current = current.getNext();

		}
		return current.getPortfolio();
	}

}
